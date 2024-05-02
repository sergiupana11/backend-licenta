package org.unstpb.wheelshare.api

import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.AuthenticationRequest
import org.unstpb.wheelshare.dto.AuthenticationResponse
import org.unstpb.wheelshare.dto.RegisterRequest
import org.unstpb.wheelshare.service.AuthenticationService
import org.unstpb.wheelshare.service.UserService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/users")
@Validated
@CrossOrigin(origins = ["http://localhost:3000"], maxAge = 3600)
class UserController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
) {
    @PostMapping("/sessions")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest,
    ): AuthenticationResponse = authenticationService.authenticate(authRequest)

    @PostMapping
    @Validated
    fun register(
        @RequestBody @Valid registerRequest: RegisterRequest,
    ): AuthenticationResponse = authenticationService.register(registerRequest)

    @GetMapping
    fun getUserInfo(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = userService.getUserBasicInfo(jwt)
}
