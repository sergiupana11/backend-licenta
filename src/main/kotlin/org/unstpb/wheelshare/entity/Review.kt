package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.Date
import java.util.UUID

@Table("review")
data class Review(
    @PrimaryKey
    var id: UUID,
    @Indexed
    var rentalId: UUID,
    @Indexed
    var posterId: UUID,
    @Indexed
    var carId: UUID,
    var text: String,
    var rating: Int,
    var dateCreated: Date,
)
