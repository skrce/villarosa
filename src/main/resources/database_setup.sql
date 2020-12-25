CREATE DATABASE `villarosa` /*!40100 DEFAULT CHARACTER SET latin1 */;

-- villarosa.apartments definition

USE villarosa;

CREATE TABLE `apartments` (
                              `id` int(8) NOT NULL AUTO_INCREMENT,
                              `capacity` int(2) DEFAULT NULL,
                              `orientation` varchar(200) DEFAULT NULL,
                              `view` varchar(200) DEFAULT NULL,
                              `regular_price` int(7) DEFAULT NULL,
                              `top_season_price` int(7) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-- villarosa.customers definition

CREATE TABLE `customers` (
                             `id` int(8) NOT NULL AUTO_INCREMENT,
                             `first_name` varchar(200) DEFAULT NULL,
                             `last_name` varchar(200) DEFAULT NULL,
                             `phone` varchar(200) DEFAULT NULL,
                             `address` varchar(200) DEFAULT NULL,
                             `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-- villarosa.reservations definition

CREATE TABLE `reservations` (
                                `id` int(8) NOT NULL AUTO_INCREMENT,
                                `customer_id` int(8) DEFAULT NULL,
                                `room_id` int(3) DEFAULT NULL,
                                `start_date` datetime DEFAULT NULL,
                                `end_date` datetime DEFAULT NULL,
                                `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



INSERT INTO villarosa.apartments (capacity,orientation,`view`,regular_price,top_season_price) VALUES
(3,'South','Lake',1500,1800),
(3,'South','Lake',1200,1500),
(6,'East + West','Garden',1800,2000),
(4,'East','Garden',1500,1800),
(4,'East','Garden',1500,1800),
(4,'East','Garden',1500,1800);