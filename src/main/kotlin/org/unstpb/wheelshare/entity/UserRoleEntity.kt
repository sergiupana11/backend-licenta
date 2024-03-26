package org.unstpb.wheelshare.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class UserRoleEntity(
    var role: UserRole,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
