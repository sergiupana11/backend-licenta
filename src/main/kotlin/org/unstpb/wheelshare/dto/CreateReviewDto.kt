package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateReviewDto(
    val rentalId: UUID,
    @field:[
    Min(1) Max(5)
    ] val rating: Int,
    @Size(min = 1, max = 3000)
    val message: String,
)
