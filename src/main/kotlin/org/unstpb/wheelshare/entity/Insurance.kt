package org.unstpb.wheelshare.entity

import org.slf4j.LoggerFactory
import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.unstpb.wheelshare.constants.Constants
import org.unstpb.wheelshare.dto.NewInsuranceRequestDto
import org.unstpb.wheelshare.entity.enums.InsuranceType
import org.unstpb.wheelshare.exception.InvalidInsuranceDurationException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Table("insurance")
data class Insurance(
    @PrimaryKey
    var id: UUID,
    @Indexed
    var beneficiaryId: UUID,
    @Indexed
    var insuranceCompanyId: UUID,
    var insuranceType: InsuranceType,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var price: Int,
    var hasBeenClaimed: Boolean,
) {
    companion object {
        private val logger: org.slf4j.Logger = LoggerFactory.getLogger(Insurance::class.java)

        fun createInsurance(
            user: User,
            newInsuranceRequest: NewInsuranceRequestDto,
            insuranceCompanyPriceModifier: Double,
        ) = Insurance(
            UUID.randomUUID(),
            user.id,
            newInsuranceRequest.insuranceCompanyId,
            newInsuranceRequest.insuranceType,
            newInsuranceRequest.startDate,
            newInsuranceRequest.endDate,
            calculateInsurancePrice(newInsuranceRequest, insuranceCompanyPriceModifier),
            false,
        )

        fun calculateInsurancePrice(
            newInsuranceRequest: NewInsuranceRequestDto,
            insuranceCompanyPriceModifier: Double,
        ): Int {
            val numWeeks = ChronoUnit.WEEKS.between(newInsuranceRequest.startDate, newInsuranceRequest.endDate)
            logger.info("Number of weeks: $numWeeks")

            val priceReductionModifier: Double =
                when {
                    numWeeks < 1 -> throw InvalidInsuranceDurationException()
                    numWeeks < 4 -> 1.0
                    numWeeks < 12 -> 0.9
                    numWeeks < 24 -> 0.75
                    else -> 0.5
                }

            val insuranceTypePriceModifier =
                when (newInsuranceRequest.insuranceType) {
                    InsuranceType.BASIC -> Constants.BASIC_INSURANCE_PRICE_MODIFIER
                    InsuranceType.MEDIUM -> Constants.MEDIUM_INSURANCE_PRICE_MODIFIER
                    InsuranceType.PREMIUM -> Constants.PREMIUM_INSURANCE_PRICE_MODIFIER
                }

            return Math.round(
                numWeeks *
                    Constants.STANDARD_INSURANCE_PRICE_PER_WEEK *
                    priceReductionModifier *
                    insuranceCompanyPriceModifier *
                    insuranceTypePriceModifier,
            ).toInt()
        }
    }
}
