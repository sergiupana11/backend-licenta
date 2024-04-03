package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.Car
import java.util.UUID

@Repository
interface CarRepository : CrudRepository<Car, UUID> {
    fun findAllByOwnerId(ownerId: UUID): List<Car>
}
