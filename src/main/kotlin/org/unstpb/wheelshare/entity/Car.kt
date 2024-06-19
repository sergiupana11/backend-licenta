package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.entity.enums.BodyType
import org.unstpb.wheelshare.entity.enums.FuelType
import org.unstpb.wheelshare.entity.enums.InsuranceType
import java.util.UUID

@Table("cars")
data class Car(
    @PrimaryKey
    var id: UUID,
    @Indexed
    var ownerId: UUID,
    var brand: String,
    var model: String,
    var fuelType: FuelType,
    var horsepower: Int,
    var description: String,
    var price: Int,
    var modelYear: String,
    var numberOfKilometers: Int,
    var fuelConsumption: Double,
    var numDoors: Int,
    var bodyType: BodyType,
    var minimumInsuranceType: InsuranceType,
) {
    fun fullName() = "$brand $model"

    constructor(ownerId: UUID, newCarRequest: AddNewCarRequest) : this(
        UUID.randomUUID(),
        ownerId,
        newCarRequest.brand,
        newCarRequest.model,
        newCarRequest.fuelType,
        newCarRequest.horsepower,
        newCarRequest.description,
        newCarRequest.price,
        newCarRequest.modelYear,
        newCarRequest.numberOfKilometers,
        newCarRequest.fuelConsumption,
        newCarRequest.numDoors,
        newCarRequest.bodyType,
        newCarRequest.minimumInsuranceType,
    )
}
