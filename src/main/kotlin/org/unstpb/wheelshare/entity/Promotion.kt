package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("promotion")
data class Promotion(
    var value: Int,
    var validFrom: LocalDateTime,
    var validUntil: LocalDateTime,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "affected_car", ordinal = 1)
    var affectedCar: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id", ordinal = 0)
    var id: UUID,
)
