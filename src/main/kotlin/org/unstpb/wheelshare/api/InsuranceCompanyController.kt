package org.unstpb.wheelshare.api

import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.NewInsuranceCompanyRequestDto
import org.unstpb.wheelshare.service.InsuranceCompanyService
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/insurance-companies")
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000"], maxAge = 3600)
class InsuranceCompanyController(
    private val insuranceCompanyService: InsuranceCompanyService,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createInsuranceCompany(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        @RequestBody newInsuranceCompanyRequestDto: NewInsuranceCompanyRequestDto,
    ) = insuranceCompanyService.createInsuranceCompany(jwtService.extractUsername(jwt), newInsuranceCompanyRequestDto)

    @GetMapping
    fun getAllInsuranceCompanies() = insuranceCompanyService.getAllInsuranceCompanies()
}
