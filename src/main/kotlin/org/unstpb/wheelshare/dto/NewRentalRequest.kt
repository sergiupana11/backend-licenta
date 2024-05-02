package org.unstpb.wheelshare.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.UUID

data class NewRentalRequest(
    val carId: UUID,
    @field:[
    DateTimeFormat(pattern = "yyyy-M-d H:m", iso = DateTimeFormat.ISO.DATE_TIME)
    JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d H:m")
    ] val startDate: LocalDateTime,
    @field:[
    DateTimeFormat(pattern = "yyyy-M-d H:m", iso = DateTimeFormat.ISO.DATE_TIME)
    JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d H:m")
    ] val endDate: LocalDateTime,
)
