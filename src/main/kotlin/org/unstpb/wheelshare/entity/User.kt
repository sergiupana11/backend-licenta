package org.unstpb.wheelshare.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
class User(
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    private var password: String,
    @Column(nullable = false, name = "first_name")
    var firstName: String,
    @Column(nullable = false, name = "last_name")
    var lastName: String,
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    var createdAt: Date,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
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
