package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.entity.UserRole
import org.unstpb.wheelshare.entity.UserRoleEntity
import org.unstpb.wheelshare.exception.UserRoleEntityNotFoundException
import org.unstpb.wheelshare.repository.UserRoleRepository

@Service
class UserRoleService(
    val userRoleRepository: UserRoleRepository,
) {
    fun getUserRoleByEnum(userRole: UserRole) = userRoleRepository.findByRole(userRole) ?: throw UserRoleEntityNotFoundException()

    fun saveRole(userRoleEntity: UserRoleEntity) = userRoleRepository.save(userRoleEntity)
}
