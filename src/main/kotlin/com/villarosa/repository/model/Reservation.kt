package com.villarosa.repository.model

import java.text.SimpleDateFormat
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "reservations")
data class Reservation(@Id
                       @GeneratedValue(strategy = GenerationType.IDENTITY)
                       val id: Int? = null,

                       @Column(name = "customer_id")
                       val customerId: Int? = null,

                       @Column(name = "room_id")
                       val roomId: Int? = null,

                       @Column(name = "start_date")
                       val startDate: Date? = null,

                       @Column(name = "end_date")
                       val endDate: Date? = null) {

    fun toReservationInfo() = ReservationInfo(id, customerId, roomId, dateFormat.format(startDate), dateFormat.format(endDate))


    companion object {
        fun buildReservation(customerId: Int, roomId: Int, startDate: Date, endDate: Date) =
                Reservation(null, customerId, roomId, startDate, endDate)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }
}

data class ReservationInfo(val id: Int? = null,
                           val customerId: Int? = null,
                           val roomId: Int? = null,
                           val startDate: String? = null,
                           val endDate: String? = null)