package org.unstpb.wheelshare.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.utils.BEARER
import java.time.Instant
import java.util.Date
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret-key}")
    val secretKey: String,
    @Value("\${jwt.token.validity}")
    val jwtExpiration: Long,
) {
    fun extractUsername(token: String): String {
        return extractClaim(token.substringAfter(BEARER), Claims::getSubject)
    }

    fun <T> extractClaim(
        token: String,
        claimsResolver: Function<Claims, T>,
    ): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun generateToken(userDetails: UserDetails) = generateToken(mutableMapOf(), userDetails)

    fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
    ) = buildToken(extraClaims, userDetails, jwtExpiration)

    fun isTokenValid(
        token: String,
        userDetails: UserDetails,
    ): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    private fun buildToken(
        extraClaims: Map<String, Any?>,
        userDetails: UserDetails,
        expiration: Long,
    ): String {
        return Jwts
            .builder()
            .issuedAt(Date.from(Instant.now()))
            .claims(extraClaims)
            .expiration(Date.from(Instant.now().plusSeconds(expiration)))
            .subject(userDetails.username)
            .signWith(getSignInKey())
            .compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date.from(Instant.now()))
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
}
