package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.entity.Car
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.UserRepository

@Service
class CarService(
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
) {
    fun getAllCarsForUser(username: String): List<Car> {
        userRepository.findByEmail(username)?.let {
            return carRepository.findAllByUserId(it.id) ?: listOf()
        } ?: throw UserNotFoundException()
    }

    fun addNewCarForUser(
        username: String,
        newCarRequest: AddNewCarRequest,
    ): Car {
        userRepository.findByEmail(username)?.let {
            return carRepository.save(
                Car(it.id, newCarRequest),
            )
        } ?: throw UserNotFoundException()
    }
}
