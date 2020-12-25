package com.villarosa.repository.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/*
    -- create table villarosa.customers (`id` int(8) not null auto_increment, `first_name` varchar(200), `last_name` varchar(200),
    -- `phone` varchar(200),  `address` varchar(200), `created_date DATETIME default now()`PRIMARY KEY (`id`));
 */

@Entity
@Table(name = "customers")
data class Customer(@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    val id: Int? = null,

                    @Column(name = "first_name")
                    val firstName: String? = null,

                    @Column(name = "last_name")
                    val lastName: String? = null,

                    @Column(name = "phone")
                    val phone: String? = null,

                    @Column(name = "address")
                    val address: String? = null) {
    companion object {
        fun buildCustomer(firstName: String, lastName: String, phone: String, address: String) =
                Customer(null, firstName, lastName, phone, address)
    }
}