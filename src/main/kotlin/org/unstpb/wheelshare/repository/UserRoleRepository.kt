package org.unstpb.wheelshare.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.UserRole
import org.unstpb.wheelshare.entity.UserRoleEntity

@Repository
interface UserRoleRepository : JpaRepository<UserRoleEntity, Long> {

    fun findByRole(role: UserRole): UserRoleEntity?
}
