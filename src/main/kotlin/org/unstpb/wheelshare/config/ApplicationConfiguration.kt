package org.unstpb.wheelshare.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.unstpb.wheelshare.repository.UserRepository
import org.unstpb.wheelshare.service.CustomUserDetailsService

@Configuration
class ApplicationConfiguration(
    private val userRepository: UserRepository,
) {
    @Bean
    fun encoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider() =
        DaoAuthenticationProvider().also {
            it.setUserDetailsService(userDetailsService())
            it.setPasswordEncoder(encoder())
        }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return CustomUserDetailsService(userRepository)
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}
