package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table("image_details")
data class ImageDetails(
    @PrimaryKey
    var id: UUID,
    @Indexed
    val carId: UUID?,
    @Indexed
    val personId: UUID?,
    val fileUri: String,
    val fileDownloadUri: String,
    val fileSize: Long,
)
