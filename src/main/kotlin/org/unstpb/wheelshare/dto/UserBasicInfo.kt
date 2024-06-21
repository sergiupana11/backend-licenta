package org.unstpb.wheelshare.dto

import java.util.UUID

data class UserBasicInfo(
    val userId: UUID,
    val firstName: String,
    val totalCars: Int,
    val incomingRequests: Int,
    val outgoingRequests: Int,
    val imageId: UUID?,
)
