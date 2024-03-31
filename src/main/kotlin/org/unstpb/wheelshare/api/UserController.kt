package org.unstpb.wheelshare.api

import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.unstpb.wheelshare.dto.AuthenticationRequest
import org.unstpb.wheelshare.dto.AuthenticationResponse
import org.unstpb.wheelshare.dto.RegisterRequest
import org.unstpb.wheelshare.service.AuthenticationService

@RestController
@RequestMapping("/api/v1/users")
@Validated
class UserController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/sessions")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest,
    ): AuthenticationResponse = authenticationService.authenticate(authRequest)

    @PostMapping
    fun register(
        @RequestBody @Valid registerRequest: RegisterRequest,
    ): AuthenticationResponse = authenticationService.register(registerRequest)
}
