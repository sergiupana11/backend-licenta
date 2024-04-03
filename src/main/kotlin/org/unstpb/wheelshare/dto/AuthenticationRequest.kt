package org.unstpb.wheelshare.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

class AuthenticationRequest(
    @Email
    val email: String,
    @Size(min = 6, max = 20)
    val password: String,
)
