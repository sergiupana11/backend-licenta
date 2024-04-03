package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.User

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User?
}
