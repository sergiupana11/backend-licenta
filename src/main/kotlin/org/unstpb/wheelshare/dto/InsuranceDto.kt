package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.Insurance
import org.unstpb.wheelshare.entity.InsuranceCompany
import org.unstpb.wheelshare.entity.User
import org.unstpb.wheelshare.entity.enums.InsuranceType
import java.time.LocalDateTime
import java.util.UUID

data class InsuranceDto(
    val id: UUID,
    val beneficiaryId: UUID,
    val beneficiaryName: String,
    val insuranceCompanyId: UUID,
    val insuranceCompanyName: String,
    val insuranceType: InsuranceType,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val price: Int,
) {
    companion object {
        fun of(
            user: User,
            insurance: Insurance,
            insuranceCompany: InsuranceCompany,
        ) = InsuranceDto(
            insurance.id,
            user.id,
            user.fullName(),
            insuranceCompany.id,
            insuranceCompany.name,
            insurance.insuranceType,
            insurance.startDate,
            insurance.endDate,
            insurance.price,
        )
    }
}
