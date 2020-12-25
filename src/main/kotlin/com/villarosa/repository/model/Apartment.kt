package com.villarosa.repository.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/*
  create table villarosa.apartments (`id` int(8) not null auto_increment, `capacity` int(2), `orientation` varchar(200),
 `view` varchar(200),  `regular_price` int(7), `top_season_price` int(7), PRIMARY KEY (`id`));
 */
@Entity
@Table(name = "apartments")
data class Apartment(@Id
                     @GeneratedValue(strategy = GenerationType.IDENTITY)
                     val id: Int? = null,

                     @Column(name = "capacity")
                     val capacity: Int? = null,

                     @Column(name = "orientation")
                     val orientation: String? = null,

                     @Column(name = "view")
                     val view: String? = null,

                     @Column(name = "regular_price")
                     val regularPrice: Int? = null,

                     @Column(name = "top_season_price")
                     val topSeasonPrice: Int? = null)
