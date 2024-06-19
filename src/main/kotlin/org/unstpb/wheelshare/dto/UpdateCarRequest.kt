package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.enums.BodyType
import org.unstpb.wheelshare.entity.enums.FuelType
import org.unstpb.wheelshare.entity.enums.InsuranceType

data class UpdateCarRequest(
    val brand: String?,
    val model: String?,
    val fuelType: FuelType?,
    val horsepower: Int?,
    val description: String?,
    val price: Int?,
    val modelYear: String?,
    val numberOfKilometers: Int?,
    val fuelConsumption: Double?,
    val numDoors: Int?,
    val bodyType: BodyType?,
    val minimumInsuranceType: InsuranceType?,
)
