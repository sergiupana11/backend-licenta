package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.Rental
import java.util.UUID

@Repository
interface RentalRepository : CrudRepository<Rental, UUID> {
    fun findAllByCarId(carId: UUID): List<Rental>

    fun findAllByRenterId(renterId: UUID): List<Rental>
}
