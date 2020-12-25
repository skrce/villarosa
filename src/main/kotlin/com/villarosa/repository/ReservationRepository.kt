package com.villarosa.repository

import com.villarosa.repository.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findByRoomId(roomId: Int): List<Reservation>
    fun findByStartDateBeforeAndEndDateAfter(endDate: Date, startDate: Date): List<Reservation>
    fun findByCustomerId(customerId: Int): List<Reservation>
}
