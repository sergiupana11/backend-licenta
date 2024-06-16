package org.unstpb.wheelshare.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.unstpb.wheelshare.entity.ImageDetails
import java.util.UUID

@Repository
interface ImageRepository : CrudRepository<ImageDetails, UUID> {
    fun findAllByCarId(carId: UUID): List<ImageDetails>

    fun deleteAllByCarId(carId: UUID)

    fun findByPersonId(personId: UUID): ImageDetails?
}
