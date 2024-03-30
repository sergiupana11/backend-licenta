package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.unstpb.wheelshare.entity.Gender

class RegisterRequest(
    @Email
    @NotBlank
    val email: String,
    @Min(value = 6)
    @Max(value = 20)
    @NotBlank
    val password: String,
    @Max(value = 50)
    @NotBlank
    val firstName: String,
    @Max(value = 50)
    @NotBlank
    val lastName: String,
    @Size(min = 10, max = 10)
    @NotBlank
    val phoneNumber: String,
    @Size(min = 10)
    @NotBlank
    val drivingLicenceNumber: String,
    @NotBlank
    val gender: Gender,
)
