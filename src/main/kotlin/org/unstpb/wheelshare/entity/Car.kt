package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.entity.enums.FuelType
import java.util.UUID

// TODO: calculate average rating and get reviews on the fly

@Table("cars")
data class Car(
    // TODO: add images
    @PrimaryKey
    var id: UUID,
    @Indexed
    var ownerId: UUID,
    val brand: String,
    val model: String,
    val fuelType: FuelType,
    val horsepower: Int,
    var description: String,
    var price: Int,
) {
    constructor(ownerId: UUID, newCarRequest: AddNewCarRequest) : this(
        UUID.randomUUID(),
        ownerId,
        newCarRequest.brand,
        newCarRequest.model,
        newCarRequest.fuelType,
        newCarRequest.horsepower,
        newCarRequest.description,
        newCarRequest.price,
    )
}
