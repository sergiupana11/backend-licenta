package org.unstpb.wheelshare.api

import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.RentalRequestSubmitAction
import org.unstpb.wheelshare.entity.Rental
import org.unstpb.wheelshare.extensions.runCatchingExceptions
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.service.RentalService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/rentals")
@Validated
@CrossOrigin(origins = ["http://localhost:3000"], maxAge = 3600)
class RentalController(
    private val rentalService: RentalService,
    private val jwtService: JwtService,
    private val logger: Logger = LoggerFactory.getLogger(RentalController::class.java),
) {
//    @PostMapping
//    fun addNewRental(
//        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
//        @RequestBody @Valid newRentalRequest: NewRentalRequest,
//    ) = logger.runCatchingExceptions {
//        rentalService.addNewRental(jwtService.extractUsername(jwt), newRentalRequest)
//    }

    @GetMapping
    fun getAllRentalsForUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = logger.runCatchingExceptions {
        rentalService.getAllRentalsForUser(jwtService.extractUsername(jwt))
    }

    @PatchMapping
    fun acceptOrDeclineRentalStatus(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody @Valid rentalRequestSubmitAction: RentalRequestSubmitAction,
    ): Rental {
        logger.info("In function")
        return rentalService.acceptOrDeclineRentalStatus(jwtService.extractUsername(jwt), rentalRequestSubmitAction)
    }
}
