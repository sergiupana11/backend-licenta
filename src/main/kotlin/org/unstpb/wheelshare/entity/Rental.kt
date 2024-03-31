package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("rental")
data class Rental(
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    var id: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    @Indexed
    var carId: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    @Indexed
    var renterId: UUID,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
)
