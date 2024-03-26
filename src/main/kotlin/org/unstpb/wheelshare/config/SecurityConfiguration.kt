package org.unstpb.wheelshare.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.unstpb.wheelshare.filter.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationProvider: DaoAuthenticationProvider,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/**")
                    .permitAll()
                    .requestMatchers("/error")
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}
