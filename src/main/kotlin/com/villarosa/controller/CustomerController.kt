package com.villarosa.controller

import com.villarosa.repository.model.Customer
import com.villarosa.service.CustomerService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(private val customerService: CustomerService) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(CustomerController::class.java)
    }

    @PostMapping(value = ["/customer"],
                 produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Creates a customer.")
    fun createCustomer(@ApiParam(value = "First name")
                       @RequestParam("firstName") firstName: String,
                       @ApiParam(value = "Last name")
                       @RequestParam("lastName") lastName: String,
                       @ApiParam(value = "Phone number")
                       @RequestParam("phone") phone: String,
                       @ApiParam(value = "Address (Country, City, Street, Number)")
                       @RequestParam("address") address: String): Int {
        LOGGER.info("Called createCustomer() with firstName {}, lastName {}, phone {}, address {}", firstName, lastName, phone, address)
        return customerService.createCustomer(firstName, lastName, phone, address)
    }

    @GetMapping(value = ["/customer"],
                produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Search customer by part of firstName or phone.")
    fun searchCustomer(@ApiParam(value = "First name")
                       @RequestParam("firstName") firstName: String?,
                       @ApiParam(value = "Phone number")
                       @RequestParam("phone") phone: String?): List<Customer> {
        LOGGER.info("Called searchCustomer() with firstName {}, phone {}", firstName, phone)
        return customerService.searchCustomer(firstName, phone)
    }
}