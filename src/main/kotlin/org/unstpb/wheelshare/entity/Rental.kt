package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.unstpb.wheelshare.entity.enums.RentalStatus
import java.time.LocalDateTime
import java.util.UUID

@Table("rental")
data class Rental(
    @PrimaryKey
    var id: UUID,
    @Indexed
    var carId: UUID,
    @Indexed
    var renterId: UUID,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var status: RentalStatus,
)
