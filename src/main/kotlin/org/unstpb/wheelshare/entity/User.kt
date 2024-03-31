package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.unstpb.wheelshare.dto.RegisterRequest
import java.time.Instant
import java.util.Date
import java.util.UUID

@Table("user")
data class User(
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    var id: UUID,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    @Indexed
    var email: String,
    private var password: String,
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var phoneNumber: String,
    var role: UserRole,
    var drivingLicenceNumber: String? = null,
    var createdAt: Date,
) : UserDetails {
    constructor(registerRequest: RegisterRequest, encodedPassword: String) : this(
        UUID.randomUUID(),
        registerRequest.email,
        encodedPassword,
        registerRequest.firstName,
        registerRequest.lastName,
        registerRequest.gender,
        registerRequest.phoneNumber,
        UserRole.USER,
        registerRequest.drivingLicenceNumber,
        Date.from(Instant.now()),
    )

    override fun getAuthorities(): MutableList<SimpleGrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
