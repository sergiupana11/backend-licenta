package org.unstpb.wheelshare.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username) ?: throw UsernameNotFoundException("Not found!")
}
