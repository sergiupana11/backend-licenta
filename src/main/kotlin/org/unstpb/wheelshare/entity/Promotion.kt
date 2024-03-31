package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("promotion")
data class Promotion(
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    var affectedCarId: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    var id: UUID,
    var value: Int,
    var validFrom: LocalDateTime,
    var validUntil: LocalDateTime,
)
