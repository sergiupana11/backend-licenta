package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("promotion")
data class Promotion(
    @PrimaryKey
    var id: UUID,
    @Indexed
    var affectedCarId: UUID,
    var value: Int,
    var validFrom: LocalDateTime,
    var validUntil: LocalDateTime,
)
