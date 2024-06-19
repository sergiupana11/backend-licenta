package org.unstpb.wheelshare.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.unstpb.wheelshare.dto.AddNewCarRequest
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
    ) = carService.getCurrentUserCars(jwtService.extractUsername(jwt))

    @PostMapping
    fun addNewCarForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody newCarRequest: AddNewCarRequest,
    ) = carService.addNewCarForUser(jwtService.extractUsername(jwt), newCarRequest)

    @GetMapping("/{carId}")
    fun getCarData(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @PathVariable carId: UUID,
    ) = carService.getCarData(jwtService.extractUsername(jwt), carId)

    @GetMapping
    fun getAvailableCarsToRent(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = carService.getAvailableCarsToRent(jwtService.extractUsername(jwt))

    @GetMapping("/{carId}/summary")
    fun getCarSummary(
        @PathVariable carId: UUID,
    ) = carService.getCarSummary(carId)

    @GetMapping("/{carId}/rentals")
    fun getRentalsForCar(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @PathVariable carId: UUID,
    ) = carService.getRentalsForCar(jwtService.extractUsername(jwt), carId)

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCar(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @PathVariable carId: UUID,
    ) = carService.deleteCar(jwtService.extractUsername(jwt), carId)
}
