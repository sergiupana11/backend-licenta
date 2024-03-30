package org.unstpb.wheelshare.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.service.CarService
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER
import org.unstpb.wheelshare.utils.BEARER

@RestController
@RequestMapping("/api/v1/cars")
class CarController(
    private val carService: CarService,
    private val jwtService: JwtService,
    private val logger: Logger? = LoggerFactory.getLogger(CarController::class.java),
) {
    @GetMapping
    fun getAllCarsForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = carService.getAllCarsForUser(getUsernameFromAccessToken(jwt))

    @PostMapping
    fun addNewCarForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody newCarRequest: AddNewCarRequest,
    ) = carService.addNewCarForUser(getUsernameFromAccessToken(jwt), newCarRequest)

    private fun getUsernameFromAccessToken(jwt: String) = jwtService.extractUsername(jwt.substringAfter(BEARER))
}
