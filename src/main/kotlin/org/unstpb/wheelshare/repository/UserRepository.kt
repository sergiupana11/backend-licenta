package org.unstpb.wheelshare.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.User

@Repository
interface UserRepository : CassandraRepository<User, String> {
//    @AllowFiltering
    fun findByEmail(email: String): User?
}
