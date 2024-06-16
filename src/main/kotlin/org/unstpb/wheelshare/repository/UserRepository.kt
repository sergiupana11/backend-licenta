package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.User
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
