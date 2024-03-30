package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("rental")
data class Rental(
    var carId: UUID,
    var renterId: UUID,
    var startDate: LocalDateTime,
    var endDateTime: LocalDateTime,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id", ordinal = 0)
    var id: UUID,
)
