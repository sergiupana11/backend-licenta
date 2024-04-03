package org.unstpb.wheelshare.dto

import java.time.LocalDateTime
import java.util.UUID

data class NewRentalRequest(
    val carId: UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
