package org.unstpb.wheelshare.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.extensions.runCatchingExceptions
import org.unstpb.wheelshare.service.CarService
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER
import java.util.UUID

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000"], maxAge = 3600)
class CarController(
    private val carService: CarService,
    private val jwtService: JwtService,
    private val logger: Logger = LoggerFactory.getLogger(CarController::class.java),
) {
    @GetMapping("/me")
    fun getAllCarsForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = logger.runCatchingExceptions {
        carService.getAllCarsForUser(jwtService.extractUsername(jwt))
    }

    @PostMapping
    fun addNewCarForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody newCarRequest: AddNewCarRequest,
    ) = logger.runCatchingExceptions {
        carService.addNewCarForUser(jwtService.extractUsername(jwt), newCarRequest)
    }

    @GetMapping("/{carId}")
    fun getCarData(
        @PathVariable carId: UUID,
    ) = carService.getCarData(carId)

    @GetMapping
    fun getAvailableCars(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = logger.runCatchingExceptions {
        carService.getAvailableCars(jwtService.extractUsername(jwt))
    }

    @GetMapping("/{carId}/summary")
    fun getCarSummary(
        @PathVariable carId: UUID,
    ) = carService.getCarSummary(carId)

    @GetMapping("/me")
    fun getCarsForCurrentUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = logger.runCatchingExceptions {
        carService.getCarsForCurrentUser(jwtService.extractUsername(jwt))
    }
}
