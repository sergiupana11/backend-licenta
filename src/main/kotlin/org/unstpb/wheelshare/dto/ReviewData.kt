package org.unstpb.wheelshare.dto

import java.util.*

data class ReviewData(
    val id: UUID,
    val posterId: UUID,
    val posterName: String,
    val carId: UUID,
    val carName: String,
    val text: String,
    val rating: Int,
    val dateCreated: Date,
)
