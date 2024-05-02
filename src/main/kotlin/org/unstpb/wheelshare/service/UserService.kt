package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.UserBasicInfo
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
) {
    fun loadByUsername(username: String) = userRepository.findByEmail(username) ?: throw UserNotFoundException()

    fun getUserBasicInfo(jwt: String): UserBasicInfo =
        userRepository.findByEmail(jwtService.extractUsername(jwt))?.let {
            UserBasicInfo(
                it.firstName,
            )
        } ?: throw UserNotFoundException()
}
