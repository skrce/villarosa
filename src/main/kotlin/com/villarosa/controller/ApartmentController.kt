package com.villarosa.controller

import com.villarosa.repository.model.Apartment
import com.villarosa.service.ApartmentService
import com.villarosa.service.CustomerService
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApartmentController(private val apartmentService: ApartmentService) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(ApartmentController::class.java)
    }

    @GetMapping(value = ["/apartment"],
                produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Lists all apartments.")
    fun getAllApartments(): List<Apartment> {
        LOGGER.info("Called getAllApartments()")
        return apartmentService.getAllApartments()
    }
}