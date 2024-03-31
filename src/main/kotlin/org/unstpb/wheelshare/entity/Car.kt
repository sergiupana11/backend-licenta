package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import org.unstpb.wheelshare.dto.AddNewCarRequest
import java.util.UUID

// TODO: calculate average rating and get reviews on the fly

@Table("cars")
data class Car(
    // TODO: add images
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    @Indexed
    var userId: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    var id: UUID,
    val brand: String,
    val model: String,
    val fuelType: FuelType,
    val horsepower: Int,
    var description: String,
) {
    constructor(userId: UUID, newCarRequest: AddNewCarRequest) : this(
        userId,
        UUID.randomUUID(),
        newCarRequest.brand,
        newCarRequest.model,
        newCarRequest.fuelType,
        newCarRequest.horsepower,
        newCarRequest.description,
    )
}
