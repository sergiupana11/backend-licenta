package org.unstpb.wheelshare.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import org.unstpb.wheelshare.constants.Constants.DATE_TIME_FORMAT
import org.unstpb.wheelshare.entity.enums.InsuranceType
import java.time.LocalDateTime
import java.util.*

class NewInsuranceRequestDto(
    val insuranceType: InsuranceType,
    @field:[
    DateTimeFormat(pattern = DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    ] val startDate: LocalDateTime,
    @field:[
    DateTimeFormat(pattern = DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    ] val endDate: LocalDateTime,
    val insuranceCompanyId: UUID,
)
