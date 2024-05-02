package org.unstpb.wheelshare.dto

import java.util.UUID

data class CarSummaryDto(
    val id: UUID,
    val brand: String,
    val model: String,
    val price: Int,
)
