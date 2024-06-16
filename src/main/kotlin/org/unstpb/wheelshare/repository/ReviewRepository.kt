package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.Review
import java.util.*

@Repository
interface ReviewRepository : CrudRepository<Review, UUID> {
    fun findAllByCarId(carId: UUID): List<Review>

    fun findAllByPosterId(posterId: UUID): List<Review>
}
