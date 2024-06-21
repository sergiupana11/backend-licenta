package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.enums.InsuranceLevel
import java.util.*

data class UserBasicInfo(
    val userId: UUID,
    val fullName: String,
    val dateCreated: Date,
    val drivingLicenceNumber: String?,
    val email: String,
    val phoneNumber: String,
    val insuranceLevel: InsuranceLevel,
    val totalCars: Int,
    val incomingRequests: Int,
    val outgoingRequests: Int,
    val averageRating: Double,
    val imageId: UUID?,
)
