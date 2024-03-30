package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table("review")
data class Review(
    var text: String,
    var rating: Int,
    var rentalId: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id", ordinal = 0)
    var id: UUID,
)
