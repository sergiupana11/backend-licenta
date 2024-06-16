package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.unstpb.wheelshare.constants.Constants
import org.unstpb.wheelshare.dto.RegisterRequest
import org.unstpb.wheelshare.entity.enums.Gender
import org.unstpb.wheelshare.entity.enums.InsuranceLevel
import org.unstpb.wheelshare.entity.enums.UserRole
import java.time.Instant
import java.util.Date
import java.util.UUID

@Table("user")
data class User(
    @PrimaryKey
    var id: UUID,
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
    var insuranceLevel: InsuranceLevel,
) : UserDetails {
    constructor(registerRequest: RegisterRequest, encodedPassword: String) : this(
        UUID.randomUUID(),
        registerRequest.email,
        encodedPassword,
        registerRequest.firstName,
        registerRequest.lastName,
        registerRequest.gender,
        registerRequest.phoneNumber,
        if (registerRequest.email == Constants.ADMIN_EMAIL) UserRole.ADMIN else UserRole.USER,
        registerRequest.drivingLicenceNumber,
        Date.from(Instant.now()),
        InsuranceLevel.B0,
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

    fun fullName() = "$firstName $lastName"
}
