package org.unstpb.wheelshare.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date
import java.util.UUID

@Table("user")
data class User(
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "email", ordinal = 1)
    var email: String,
    private var password: String,
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var phoneNumber: String,
    var role: UserRole,
    var drivingLicenceNumber: String? = null,
    var createdAt: Date,
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id", ordinal = 0)
    var id: UUID,
) : UserDetails {
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
