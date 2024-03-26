package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.Gender

class ClientRegisterDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val gender: Gender,
)
