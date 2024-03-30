package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun loadByUsername(username: String) = userRepository.findByEmail(username) ?: throw UserNotFoundException()
}
