package org.unstpb.wheelshare.api

import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.NewRentalRequest
import org.unstpb.wheelshare.dto.RentalRequestSubmitAction
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.service.RentalService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/rentals")
@CrossOrigin(origins = ["http://localhost:3000"], maxAge = 3600)
class RentalController(
    private val rentalService: RentalService,
    private val jwtService: JwtService,
    private val logger: Logger = LoggerFactory.getLogger(RentalController::class.java),
) {
    @PostMapping
    fun createRentalRequest(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody @Valid newRentalRequest: NewRentalRequest,
    ) = rentalService.createRentalRequest(jwtService.extractUsername(jwt), newRentalRequest)

    @GetMapping
    fun getAllRentalsForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = rentalService.getAllRentalsForUser(jwtService.extractUsername(jwt))

    @PatchMapping
    fun submitRentalAction(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody @Valid rentalRequestSubmitAction: RentalRequestSubmitAction,
    ) = rentalService.submitRentalAction(jwtService.extractUsername(jwt), rentalRequestSubmitAction)
}
