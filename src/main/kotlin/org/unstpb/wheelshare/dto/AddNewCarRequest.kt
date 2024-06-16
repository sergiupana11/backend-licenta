package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.unstpb.wheelshare.annotations.ValidatedEnum
import org.unstpb.wheelshare.entity.enums.BodyType
import org.unstpb.wheelshare.entity.enums.FuelType
import org.unstpb.wheelshare.entity.enums.InsuranceType

class AddNewCarRequest(
    @field:NotBlank
    val brand: String,
    @field:NotBlank
    val model: String,
    @field:NotBlank
    val fuelType: FuelType,
    @field:Min(1)
    val horsepower: Int,
    @field:[
    NotBlank Size(max = 1000)
    ] val description: String,
    @field:[
    NotBlank Min(1)
    ] val price: Int,
    @field:[
    NotBlank Min(1900)
    ] val modelYear: String,
    @field:[
    NotBlank Min(1)
    ] val numberOfKilometers: Int,
    @field:[
    NotBlank Min(1)
    ] val fuelConsumption: Double,
    @field:[
    NotBlank Min(1)
    ] val numDoors: Int,
    @field:[
    NotBlank ValidatedEnum("COUPE|SEDAN|CONVERTIBLE|HATCHBACK|PICKUP|SUV|MINIVAN|ESTATE")
    ] val bodyType: BodyType,
    @field:[
    ValidatedEnum("BASIC|MEDIUM|PREMIUM")
    ] val minimumInsuranceType: InsuranceType,
)
