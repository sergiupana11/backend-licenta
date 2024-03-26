package org.unstpb.wheelshare.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.unstpb.wheelshare.dto.AuthenticationResponse
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER

@RestController
@RequestMapping("/api/v1/hello")
class HelloWorldController {
    @GetMapping
    fun helloWorld(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ): AuthenticationResponse {
        return AuthenticationResponse("hello")
    }
}
