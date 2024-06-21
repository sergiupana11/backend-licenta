package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.RentalDto
import org.unstpb.wheelshare.dto.UserBasicInfo
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.ImageRepository
import org.unstpb.wheelshare.repository.RentalRepository
import org.unstpb.wheelshare.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository,
    private val imageRepository: ImageRepository,
) {
    fun loadByUsername(username: String) = userRepository.findByEmail(username) ?: throw UserNotFoundException()

    fun getUserInfo(jwt: String): UserBasicInfo {
        val username = jwtService.extractUsername(jwt)
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val totalCars = carRepository.findAllByOwnerId(user.id).count()

        val incomingRequests =
            carRepository.findAllByOwnerId(user.id).flatMap { car ->
                rentalRepository.findAllByCarId(car.id).map { rental ->
                    val renter = userRepository.findById(rental.renterId).orElseThrow { UserNotFoundException() }

                    RentalDto.of(rental, car, renter, owner = user)
                }
            }.count()

        val outgoingRequests = rentalRepository.findAllByRenterId(user.id).count()

        val imageId = imageRepository.findByPersonId(user.id)?.id

        return UserBasicInfo(
            user.id,
            user.firstName,
            totalCars,
            incomingRequests,
            outgoingRequests,
            imageId,
        )
    }
}
