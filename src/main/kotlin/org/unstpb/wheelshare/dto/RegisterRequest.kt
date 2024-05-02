package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.unstpb.wheelshare.annotations.ValidatedEnum
import org.unstpb.wheelshare.entity.enums.Gender

@Validated
data class RegisterRequest(
    @field:[
    Email
    ] val email: String,
    @field:[
    Size(min = 6, max = 20)
    NotBlank
    ] val password: String,
    @field:[
    Size(max = 50)
    NotBlank
    ] val firstName: String,
    @field:[
    Size(max = 50) NotBlank
    ] val lastName: String,
    @field:[
    Size(min = 10, max = 10)
    ] val phoneNumber: String,
    @field:[
    NotBlank
    ] val drivingLicenceNumber: String,
    @field:[
    ValidatedEnum(regex = "MALE|FEMALE|OTHER")
    ] val gender: Gender,
)
