package org.unstpb.wheelshare.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.*
import org.unstpb.wheelshare.entity.Car
import org.unstpb.wheelshare.entity.enums.RentalStatus
import org.unstpb.wheelshare.exception.CarAlreadyInUseException
import org.unstpb.wheelshare.exception.CarNotFoundException
import org.unstpb.wheelshare.exception.NoPermissionForActionException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.ImageRepository
import org.unstpb.wheelshare.repository.RentalRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.util.UUID

@Service
class CarService(
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository,
    private val imageRepository: ImageRepository,
    private val logger: Logger = LoggerFactory.getLogger(CarService::class.java),
) {
    fun getCurrentUserCars(username: String): List<CarSummaryDto> {
        userRepository.findByEmail(username)?.let {
            return carRepository.findAllByOwnerId(it.id).map { car ->
                val mainImageId = getMainImageId(car)
                CarSummaryDto(
                    car.id,
                    car.brand,
                    car.model,
                    car.price,
                    mainImageId,
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

    fun getCarData(
        username: String,
        carId: UUID,
    ): CarDto {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        logger.info("Trying to get car with id $carId")
        val car =
            carRepository.findById(carId).orElseThrow {
                CarNotFoundException()
            }

        logger.info("Read car from database with id ${car.id}")

        val imageIds =
            imageRepository.findAllByCarId(carId).map {
                it.id
            }

        val owner = userRepository.findById(car.ownerId).orElseThrow { UserNotFoundException() }
        val isOwner = user.id == owner.id

        return CarDto(car, owner.fullName(), isOwner, imageIds, owner.phoneNumber)
    }

    fun getAvailableCarsToRent(username: String): List<CarSummaryDto> {
        userRepository.findByEmail(username)?.let { user ->
            return carRepository.findAll().filter {
                it.ownerId != user.id
            }.map {
                val mainImageId = getMainImageId(it)
                CarSummaryDto(
                    it.id,
                    it.brand,
                    it.model,
                    it.price,
                    mainImageId,
                )
            }
        } ?: throw UserNotFoundException()
    }

    fun getCarSummary(carId: UUID): CarSummaryDto {
        val car =
            carRepository.findById(carId).orElseThrow {
                CarNotFoundException()
            }

        val mainImageId = getMainImageId(car)

        return CarSummaryDto(
            car.id,
            car.brand,
            car.model,
            car.price,
            mainImageId,
        )
    }

    fun getRentalsForCar(
        username: String,
        carId: UUID,
    ): List<RentalDto> {
        val owner = userRepository.findByEmail(username) ?: throw UserNotFoundException()
        val car =
            carRepository.findById(carId).orElseThrow {
                CarNotFoundException()
            }

        if (car.ownerId != owner.id) {
            throw NoPermissionForActionException()
        }

        return rentalRepository.findAllByCarId(car.id).map {
            val renter = userRepository.findById(it.renterId).orElseThrow { UserNotFoundException() }

            RentalDto.of(it, car, renter, owner)
        }
    }

    fun deleteCar(
        username: String,
        carId: UUID,
    ) {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        carRepository.findById(carId).orElseThrow { CarNotFoundException() }.let { car ->
            if (rentalRepository.findAllByCarId(car.id).count { it.status == RentalStatus.ACCEPTED } > 0) {
                throw CarAlreadyInUseException()
            }

            if (car.ownerId != user.id) {
                throw NoPermissionForActionException()
            }

            imageRepository.deleteAllByCarId(carId)

            carRepository.deleteById(car.id)
        }
    }

    fun updateCar(
        username: String,
        carId: UUID,
        updateCarRequest: UpdateCarRequest,
    ): Car {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val car = carRepository.findById(carId).orElseThrow { CarNotFoundException() }

        if (car.ownerId != user.id) {
            throw NoPermissionForActionException()
        }

        updateCarRequest.brand?.let { car.brand = it }
        updateCarRequest.model?.let { car.model = it }
        updateCarRequest.fuelType?.let { car.fuelType = it }
        updateCarRequest.horsepower?.let { car.horsepower = it }
        updateCarRequest.description?.let { car.description = it }
        updateCarRequest.price?.let { car.price = it }
        updateCarRequest.modelYear?.let { car.modelYear = it }
        updateCarRequest.numberOfKilometers?.let { car.numberOfKilometers = it }
        updateCarRequest.fuelConsumption?.let { car.fuelConsumption = it }
        updateCarRequest.numDoors?.let { car.numDoors = it }
        updateCarRequest.bodyType?.let { car.bodyType = it }
        updateCarRequest.minimumInsuranceType?.let { car.minimumInsuranceType = it }

        return carRepository.save(car)
    }

    private fun getMainImageId(car: Car): UUID? {
        imageRepository.findAllByCarId(car.id).let {
            if (it.isNotEmpty()) {
                return it[0].id
            }

            return null
        }
    }
}
