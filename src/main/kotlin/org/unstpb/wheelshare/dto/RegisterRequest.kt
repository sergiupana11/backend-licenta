package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.Gender
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterRequest(
    @Email
    @NotBlank
    val email: String,
    @Size(min = 6, max = 20)
    @NotBlank
    val password: String,
    @Size(max = 50)
    @NotBlank
    val firstName: String,
    @Size(max = 50)
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
