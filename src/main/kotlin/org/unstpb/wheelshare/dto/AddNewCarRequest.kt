package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.unstpb.wheelshare.entity.enums.FuelType

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
)
