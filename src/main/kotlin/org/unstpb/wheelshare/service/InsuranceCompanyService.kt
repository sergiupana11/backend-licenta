package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.InsuranceCompanyDto
import org.unstpb.wheelshare.dto.InsuranceCompanyIdWrapper
import org.unstpb.wheelshare.dto.NewInsuranceCompanyRequestDto
import org.unstpb.wheelshare.entity.InsuranceCompany
import org.unstpb.wheelshare.entity.User
import org.unstpb.wheelshare.entity.enums.UserRole
import org.unstpb.wheelshare.exception.NotEnoughPrivilegesException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.InsuranceCompanyRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.util.UUID

@Service
class InsuranceCompanyService(
    private val insuranceCompanyRepository: InsuranceCompanyRepository,
    private val userRepository: UserRepository,
) {
    fun createInsuranceCompany(
        username: String,
        newInsuranceCompanyRequestDto: NewInsuranceCompanyRequestDto,
    ): InsuranceCompanyIdWrapper {
        checkUserPrivileges(username)

        InsuranceCompany(
            UUID.randomUUID(),
            newInsuranceCompanyRequestDto.name,
            newInsuranceCompanyRequestDto.priceModifier,
        ).let {
            insuranceCompanyRepository.save(it)

            return InsuranceCompanyIdWrapper(it.id)
        }
    }

    fun getAllInsuranceCompanies(): List<InsuranceCompanyDto> {
        return insuranceCompanyRepository.findAll().map {
            InsuranceCompanyDto(
                it.id,
                it.name,
            )
        }
    }

    private fun checkUserPrivileges(username: String): User {
        return userRepository.findByEmail(username)?.also {
            if (it.role != UserRole.ADMIN) {
                throw NotEnoughPrivilegesException()
            }
        } ?: throw UserNotFoundException()
    }
}
