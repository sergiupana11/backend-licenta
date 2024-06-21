package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.RentalDto
import org.unstpb.wheelshare.dto.UserBasicInfo
import org.unstpb.wheelshare.entity.enums.RentalStatus
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository,
    private val imageRepository: ImageRepository,
    private val reviewRepository: ReviewRepository,
) {
    fun loadByUsername(username: String) = userRepository.findByEmail(username) ?: throw UserNotFoundException()

    fun getUserInfo(jwt: String): UserBasicInfo {
        val username = jwtService.extractUsername(jwt)
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val totalCars = carRepository.findAllByOwnerId(user.id).count()
        val imageId = imageRepository.findByPersonId(user.id)?.id
        val outgoingRequests = rentalRepository.findAllByRenterId(user.id).count { it.status == RentalStatus.ACCEPTED }
        val incomingRequests =
            carRepository.findAllByOwnerId(user.id).flatMap { car ->
                rentalRepository.findAllByCarId(car.id).map { rental ->
                    val renter = userRepository.findById(rental.renterId).orElseThrow { UserNotFoundException() }

                    RentalDto.of(rental, car, renter, owner = user)
                }
            }.count { it.status == RentalStatus.ACCEPTED }

        // Get all user's cars
        val cars = carRepository.findAllByOwnerId(user.id)

        // Calculate average rating for each car and then the average rating of the user
        val carRatings =
            cars.mapNotNull { car ->
                val reviews = reviewRepository.findAllByCarId(car.id)
                if (reviews.isNotEmpty()) {
                    val averageCarRating = reviews.map { it.rating }.average()
                    averageCarRating
                } else {
                    null
                }
            }

        val averageUserRating =
            if (carRatings.isNotEmpty()) {
                carRatings.average()
            } else {
                0.0 // or null, or any other default value
            }

        return UserBasicInfo(
            user.id,
            user.fullName(),
            user.createdAt,
            user.drivingLicenceNumber,
            user.email,
            user.phoneNumber,
            user.insuranceLevel,
            totalCars,
            incomingRequests,
            outgoingRequests,
            averageUserRating,
            imageId,
        )
    }
}
