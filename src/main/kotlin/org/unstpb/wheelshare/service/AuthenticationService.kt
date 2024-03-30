package org.unstpb.wheelshare.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.AuthenticationRequest
import org.unstpb.wheelshare.dto.AuthenticationResponse
import org.unstpb.wheelshare.dto.RegisterRequest
import org.unstpb.wheelshare.entity.User
import org.unstpb.wheelshare.entity.UserRole
import org.unstpb.wheelshare.exception.UserAlreadyExistsException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.UserRepository
import java.time.Instant
import java.util.*

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw UserAlreadyExistsException()
        }

        User(
            request.email,
            passwordEncoder.encode(request.password),
            request.firstName,
            request.lastName,
            request.gender,
            request.phoneNumber,
            createdAt = Date.from(Instant.now()),
            role = UserRole.USER,
            drivingLicenceNumber = request.drivingLicenceNumber,
            id = UUID.randomUUID(),
        ).let {
            userRepository.save(it)

            return AuthenticationResponse(jwtService.generateToken(it))
        }
    }

    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password,
            ),
        )

        (userRepository.findByEmail(authenticationRequest.email) ?: throw UserNotFoundException()).let {
            return AuthenticationResponse(jwtService.generateToken(it))
        }
    }
}
