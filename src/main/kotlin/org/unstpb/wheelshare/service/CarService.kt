package org.unstpb.wheelshare.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.AddNewCarRequest
import org.unstpb.wheelshare.dto.CarSummaryDto
import org.unstpb.wheelshare.entity.Car
import org.unstpb.wheelshare.exception.CarNotFoundException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.util.UUID

@Service
class CarService(
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val logger: Logger = LoggerFactory.getLogger(CarService::class.java),
) {
    fun getAllCarsForUser(username: String): List<CarSummaryDto> {
        userRepository.findByEmail(username)?.let {
            return carRepository.findAllByOwnerId(it.id).map { car ->
                CarSummaryDto(
                    car.id,
                    car.brand,
                    car.model,
                    car.price,
                )
            }
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

    fun getCarData(carId: UUID): Car {
        val car =
            carRepository.findById(carId).orElseThrow {
                CarNotFoundException()
            }

        logger.info("Read car from database with id ${car.id}")

        return car
    }

    fun getAvailableCars(username: String): List<CarSummaryDto> {
        userRepository.findByEmail(username)?.let { user ->
            return carRepository.findAll().filter {
                it.ownerId != user.id
            }.map {
                CarSummaryDto(
                    it.id,
                    it.brand,
                    it.model,
                    it.price,
                )
            }
        } ?: throw UserNotFoundException()
    }

    fun getCarSummary(carId: UUID): CarSummaryDto {
        val car =
            carRepository.findById(carId).orElseThrow {
                CarNotFoundException()
            }

        return CarSummaryDto(
            car.id,
            car.brand,
            car.model,
            car.price,
        )
    }

    fun getCarsForCurrentUser(username: String): List<Car> {
        userRepository.findByEmail(username)?.let {
            return carRepository
                .findAllByOwnerId(it.id)
        } ?: throw UserNotFoundException()
    }
}
