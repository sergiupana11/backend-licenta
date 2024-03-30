package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

// TODO: calculate average rating and get reviews on the fly

@Table("cars")
data class Car(
    // TODO: add images
    val brand: String,
    val model: String,
    val fuelType: FuelType,
    val horsepower: Int,
    var description: String,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "user_id", ordinal = 1)
    var userId: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id", ordinal = 0)
    var id: UUID,
)
