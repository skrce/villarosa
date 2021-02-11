package com.villarosa.service

import com.villarosa.exception.ExceptionHandler
import com.villarosa.repository.ReservationRepository
import com.villarosa.repository.model.Apartment
import com.villarosa.repository.model.Reservation
import com.villarosa.repository.model.ReservationInfo
import org.springframework.stereotype.Service
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@Service
class ReservationService(private val reservationRepository: ReservationRepository,
                         private val apartmentService: ApartmentService,
                         private val customerService: CustomerService) {

    fun createReservation(customerId: Int, roomId: Int, startDateString: String, endDateString: String): Int {
        if (listOf(startDateString, endDateString).any { it.isEmpty() }) {
            throw IllegalArgumentException("Dates should not be empty")
        }


        val startDate = parseDate(startDateString)

        val endDate = parseDate(endDateString)

        if (startDate > endDate) {
            throw ExceptionHandler.ClientException("StartDate can't be after EndDate")
        }

        val customer = customerService.findCustomerById(customerId)
        if (customer.isPresent.not()) {
            throw ExceptionHandler.ClientException("Customer does not exist!")
        }

        // check if reservation exists in that period
        // sql could be improved to filter more on read
        val existingReservationsForRoom = reservationRepository.findByRoomId(roomId)

        val overlappingReservations = findOverlappingReservations(existingReservationsForRoom, startDate, endDate)

        if (overlappingReservations.isNotEmpty()) {
            throw ExceptionHandler.ClientException("Existing overlapping reservations exists: " + overlappingReservations.joinToString())
        }

        return reservationRepository.save(Reservation.buildReservation(customerId, roomId, startDate, endDate))
                .id!!
    }

    fun findAvailableApartments(startDateString: String, endDateString: String): List<Apartment> {
        val startDate = parseDate(startDateString)
        val endDate = parseDate(endDateString)

        if (startDate > endDate) {
            throw ExceptionHandler.ClientException("StartDate can't be after EndDate")
        }

        val allApartments = apartmentService.getAllApartments()
        val potentialOverlappingReservations = reservationRepository.findByStartDateBeforeAndEndDateAfter(endDate, startDate)

        // rooms booked during any day in this period
        val overlappingReservationsRoomIds = findOverlappingReservations(potentialOverlappingReservations, startDate, endDate)
                .map { it.roomId }

        return allApartments.filter { it.id !in overlappingReservationsRoomIds }
    }

    fun cancelReservation(reservationId: Int) {
        val reservationOptional = reservationRepository.findById(reservationId)
        if (reservationOptional.isPresent.not()) {
            throw ExceptionHandler.ClientException("Reservation does not exist.")
        }
        val reservation = reservationOptional.get()
        reservationRepository.delete(reservation)
    }

    /**
     * Updates the room of the reservation
     */
    fun updateReservationRoom(reservationId: Int, newRoomId: Int) {
        val reservationOptional = reservationRepository.findById(reservationId)
        if (reservationOptional.isPresent.not()) {
            throw ExceptionHandler.ClientException("Reservation does not exist.")
        }

        apartmentService.findApartmentById(newRoomId)
                ?: throw ExceptionHandler.ClientException("Room does not exist.")

        val reservation = reservationOptional.get()

        val newRoomReservations = reservationRepository.findByRoomId(newRoomId)

        val newRoomReservationForPeriodOfExistingReservation =
                findOverlappingReservations(newRoomReservations, reservation.startDate!!, reservation.endDate!!)
        // check if new room has existing reservation that overlaps with the one we want to change from
        if (newRoomReservationForPeriodOfExistingReservation.isNotEmpty()) {
            val newRoomOverlappingReservation = newRoomReservationForPeriodOfExistingReservation.first()
            throw ExceptionHandler.ClientException("New room has overlapping reservation: " +
                                                           "customer-id: ${newRoomOverlappingReservation.customerId}, " +
                                                           "start-date: ${newRoomOverlappingReservation.startDate}, " +
                                                           "end-date: ${newRoomOverlappingReservation.endDate}")
        }

        val updatedReservation = reservation.copy(roomId = newRoomId)
        reservationRepository.save(updatedReservation)
    }

    /**
     * All reservations for a customer, most recent at the top.
     */
    fun findReservationsByCustomer(customerId: Int): List<ReservationInfo> =
            reservationRepository.findByCustomerId(customerId)
                    .map { it.toReservationInfo() }
                    .sortedBy { it.startDate }
                    .reversed()

    /**
     * Finds all input reservation that overlaps even in one day with the provided date range.
     * StartDate is inclusive, EndDate is exclusive.
     * For example: reservation from 2020-01-01 to 2020-01-10 means the room will be occupied on days 01 until 09, leaving day 2020-01-10 available.
     */
    private fun findOverlappingReservations(reservations: List<Reservation>, startDate: Date, endDate: Date) =
            reservations.filter {
                (startDate < it.startDate && endDate > it.startDate) ||
                        (startDate < it.endDate && endDate > it.endDate) ||
                        (startDate < it.endDate && endDate > it.startDate) ||
                        (startDate < it.startDate && endDate > it.endDate)
            }

    private fun parseDate(startDateString: String) = try {
        SimpleDateFormat("yyyy-MM-dd").parse(startDateString)
    } catch (e: ParseException) {
        throw ExceptionHandler.ClientException("Date should have format: yyyy-MM-dd")
    }
}