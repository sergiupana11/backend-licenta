package org.unstpb.wheelshare.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.Car
import java.util.UUID

@Repository
interface CarRepository : CassandraRepository<Car, Long> {
    fun findAllByUserId(userId: UUID): List<Car>?
}