package org.unstpb.wheelshare.dto

import org.unstpb.wheelshare.entity.Car
import org.unstpb.wheelshare.entity.Rental
import org.unstpb.wheelshare.entity.User
import org.unstpb.wheelshare.entity.enums.RentalStatus
import java.time.LocalDateTime
import java.util.*

data class RentalDto(
    val id: UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val carId: UUID,
    val carName: String,
    val price: Int,
    val ownerId: UUID,
    val ownerName: String,
    val renterId: UUID,
    val renterName: String,
    val status: RentalStatus,
) {
    companion object {
        fun of(
            rental: Rental,
            car: Car,
            renter: User,
            owner: User,
        ) = RentalDto(
            rental.id,
            rental.startDate,
            rental.endDate,
            rental.carId,
            car.fullName(),
            car.price,
            owner.id,
            owner.fullName(),
            renter.id,
            renter.fullName(),
            rental.status,
        )
    }
}
