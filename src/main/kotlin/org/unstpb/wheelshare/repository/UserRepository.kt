package org.unstpb.wheelshare.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
