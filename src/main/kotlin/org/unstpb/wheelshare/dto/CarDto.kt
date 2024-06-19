package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.Car
import org.unstpb.wheelshare.entity.enums.BodyType
import org.unstpb.wheelshare.entity.enums.FuelType
import org.unstpb.wheelshare.entity.enums.InsuranceType
import java.util.UUID

data class CarDto(
    var id: UUID,
    var ownerId: UUID,
    var ownerName: String,
    var ownerPhoneNumber: String,
    val brand: String,
    val model: String,
    val fuelType: FuelType,
    val horsepower: Int,
    var description: String,
    var price: Int,
    val modelYear: String,
    val numberOfKilometers: Int,
    val fuelConsumption: Double,
    val numDoors: Int,
    val bodyType: BodyType,
    val minimumInsuranceType: InsuranceType,
    val isOwner: Boolean,
    val imageIds: List<UUID>,
) {
    constructor(car: Car, ownerName: String, isOwner: Boolean, imageIds: List<UUID>, ownerPhoneNumber: String) : this(
        car.id,
        car.ownerId,
        ownerName,
        ownerPhoneNumber,
        car.brand,
        car.model,
        car.fuelType,
        car.horsepower,
        car.description,
        car.price,
        car.modelYear,
        car.numberOfKilometers,
        car.fuelConsumption,
        car.numDoors,
        car.bodyType,
        car.minimumInsuranceType,
        isOwner,
        imageIds,
    )
}
