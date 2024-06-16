package org.unstpb.wheelshare.api

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.NewInsuranceRequestDto
import org.unstpb.wheelshare.service.InsuranceService
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER
import java.util.UUID

@RestController
@RequestMapping("/api/v1/insurances")
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000"], maxAge = 3600)
class InsuranceController(
    private val insuranceService: InsuranceService,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createInsurance(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody @Valid newInsuranceRequest: NewInsuranceRequestDto,
    ) = insuranceService.createInsurance(jwtService.extractUsername(jwt), newInsuranceRequest)

    @PostMapping("/offers")
    fun getInsuranceOffer(
        @RequestBody @Valid newInsuranceRequest: NewInsuranceRequestDto,
    ) = insuranceService.getInsuranceOffer(newInsuranceRequest)

    @GetMapping("/{insuranceId}")
    fun getInsurance(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @PathVariable insuranceId: UUID,
    ) = insuranceService.getInsurance(jwtService.extractUsername(jwt), insuranceId)

    @GetMapping
    fun getInsuranceIdForCurrentUser(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = insuranceService.getInsuranceForUser(jwtService.extractUsername(jwt))
}
