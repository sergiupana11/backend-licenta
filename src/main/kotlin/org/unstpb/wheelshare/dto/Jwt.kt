package org.unstpb.wheelshare.dto

class JwtRequest(val username: String, val password: String)

class JwtResponse(val jwtToken: String)
