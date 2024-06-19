package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.InsuranceDto
import org.unstpb.wheelshare.dto.InsuranceIdWrapper
import org.unstpb.wheelshare.dto.InsuranceOfferDto
import org.unstpb.wheelshare.dto.NewInsuranceRequestDto
import org.unstpb.wheelshare.entity.Insurance
import org.unstpb.wheelshare.entity.User
import org.unstpb.wheelshare.exception.*
import org.unstpb.wheelshare.repository.InsuranceCompanyRepository
import org.unstpb.wheelshare.repository.InsuranceRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@Service
class InsuranceService(
    private val insuranceRepository: InsuranceRepository,
    private val insuranceCompanyRepository: InsuranceCompanyRepository,
    private val userRepository: UserRepository,
) {
    fun createInsurance(
        username: String,
        newInsuranceRequest: NewInsuranceRequestDto,
    ): InsuranceIdWrapper {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        insuranceRepository.findByBeneficiaryId(user.id)?.let {
            if (it.endDate.isBefore(LocalDateTime.now())) {
                throw UserAlreadyHasValidInsuranceException()
            }
        }

        val userHasValidInsurance =
            insuranceRepository.findAllByBeneficiaryId(user.id).any {
                it.endDate.isAfter(LocalDateTime.now()) && it.startDate.isBefore(LocalDateTime.now())
            }

        if (userHasValidInsurance) {
            throw UserAlreadyHasValidInsuranceException()
        }

        if (newInsuranceRequest.startDate.isAfter(newInsuranceRequest.endDate)) {
            throw InvalidDateException()
        }

        val insuranceCompanyPriceModifier =
            getInsuranceCompanyPriceModifier(newInsuranceRequest)

        Insurance.createInsurance(user, newInsuranceRequest, insuranceCompanyPriceModifier).let {
            insuranceRepository.save(it)
            return InsuranceIdWrapper(it.id)
        }
    }

    fun getInsuranceOffer(newInsuranceRequest: NewInsuranceRequestDto): InsuranceOfferDto {
        getInsuranceCompanyPriceModifier(newInsuranceRequest).let {
            return InsuranceOfferDto(
                Insurance.calculateInsurancePrice(newInsuranceRequest, it),
            )
        }
    }

    private fun getInsuranceCompanyPriceModifier(newInsuranceRequest: NewInsuranceRequestDto) =
        insuranceCompanyRepository
            .findById(newInsuranceRequest.insuranceCompanyId)
            .orElseThrow { InsuranceCompanyNotFoundException() }
            .priceModifier

    fun getInsurance(
        username: String,
        insuranceId: UUID,
    ): InsuranceDto {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()
        val insurance = insuranceRepository.findById(insuranceId).orElseThrow { InsuranceNotFoundException() }

        return insuranceDto(insurance, user)
    }

    fun getInsuranceForUser(username: String): InsuranceDto {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()
        val insurance =
            insuranceRepository
                .findAllByBeneficiaryId(user.id).maxByOrNull { it.endDate }
                ?.also {
                    if (it.endDate.isBefore(LocalDateTime.now())) {
                        throw InsuranceNotFoundException()
                    }
                } ?: throw InsuranceNotFoundException()

        return insuranceDto(insurance, user)
    }

    private fun insuranceDto(
        insurance: Insurance,
        user: User,
    ): InsuranceDto {
        val insuranceCompany =
            insuranceCompanyRepository.findById(insurance.insuranceCompanyId)
                .orElseThrow { InsuranceCompanyNotFoundException() }

        return InsuranceDto.of(user, insurance, insuranceCompany)
    }
}
