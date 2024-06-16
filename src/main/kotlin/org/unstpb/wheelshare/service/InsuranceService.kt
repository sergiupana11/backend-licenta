package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.InsuranceDto
import org.unstpb.wheelshare.dto.InsuranceIdWrapper
import org.unstpb.wheelshare.dto.InsuranceOfferDto
import org.unstpb.wheelshare.dto.NewInsuranceRequestDto
import org.unstpb.wheelshare.entity.Insurance
import org.unstpb.wheelshare.exception.InsuranceCompanyNotFoundException
import org.unstpb.wheelshare.exception.InsuranceNotFoundException
import org.unstpb.wheelshare.exception.InvalidDateException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.InsuranceCompanyRepository
import org.unstpb.wheelshare.repository.InsuranceRepository
import org.unstpb.wheelshare.repository.UserRepository
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
        val insuranceCompany =
            insuranceCompanyRepository
                .findById(insurance.insuranceCompanyId)
                .orElseThrow { InsuranceCompanyNotFoundException() }

        return InsuranceDto.of(user, insurance, insuranceCompany)
    }

    fun getInsuranceForUser(username: String): InsuranceIdWrapper {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        insuranceRepository.findByBeneficiaryId(user.id)?.let {
            return InsuranceIdWrapper(it.id)
        } ?: throw InsuranceNotFoundException()
    }
}
