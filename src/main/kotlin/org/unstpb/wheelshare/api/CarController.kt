package org.unstpb.wheelshare.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.service.CarService
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = ["http://localhost:3000"], maxAge = 3600)
class CarController(
    private val carService: CarService,
    private val jwtService: JwtService,
    private val logger: Logger? = LoggerFactory.getLogger(CarController::class.java),
) {
    @GetMapping
    fun getAllCarsForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = carService.getAllCarsForUser(jwtService.extractUsername(jwt))

    @PostMapping
    fun addNewCarForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody newCarRequest: AddNewCarRequest,
    ) = carService.addNewCarForUser(jwtService.extractUsername(jwt), newCarRequest)
}
