package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.unstpb.wheelshare.entity.FuelType

class AddNewCarRequest(
    @NotBlank
    val brand: String,
    @NotBlank
    val model: String,
    @NotBlank
    val fuelType: FuelType,
    @Min(1)
    val horsepower: Int,
    @NotBlank
    @Max(1000)
    val description: String,
)
