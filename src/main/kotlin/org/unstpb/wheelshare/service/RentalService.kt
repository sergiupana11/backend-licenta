package org.unstpb.wheelshare.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.NewRentalRequest
import org.unstpb.wheelshare.dto.RentalRequestSubmitAction
import org.unstpb.wheelshare.entity.Rental
import org.unstpb.wheelshare.entity.enums.RentalAction
import org.unstpb.wheelshare.entity.enums.RentalStatus
import org.unstpb.wheelshare.exception.CarNotFoundException
import org.unstpb.wheelshare.exception.InvalidRentalRequestException
import org.unstpb.wheelshare.exception.RentalNotFoundException
import org.unstpb.wheelshare.exception.UserNotFoundException
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.RentalRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.util.UUID

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val logger: Logger = LoggerFactory.getLogger(RentalService::class.java),
) {
    fun addNewRental(
        username: String,
        newRentalRequest: NewRentalRequest,
    ): Rental {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        logger.info("User with id: ${user.id}, made rental request for car with id: ${newRentalRequest.carId}")

        val car =
            carRepository.findById(newRentalRequest.carId).orElseThrow {
                CarNotFoundException()
            }

        logger.info("Owner id: ${car.ownerId}")

        // TODO: check if car is available in that timeframe

        Rental(
            UUID.randomUUID(),
            newRentalRequest.carId,
            user.id,
            car.ownerId,
            newRentalRequest.startDate,
            newRentalRequest.endDate,
            RentalStatus.PENDING,
        ).let {
            return rentalRepository.save(it)
        }
    }

    fun getAllRentalsForUser(username: String): List<Rental> {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val myRentals =
            listOf(
                rentalRepository.findAllByRenterId(user.id),
                rentalRepository.findAllByOwnerId(user.id),
            ).flatten()

        return myRentals
    }

    fun acceptOrDeclineRentalStatus(
        username: String,
        rentalRequestSubmitAction: RentalRequestSubmitAction,
    ): Rental {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val rental =
            rentalRepository.findById(rentalRequestSubmitAction.rentalId).orElseThrow {
                RentalNotFoundException()
            }

        val car =
            carRepository.findById(rental.carId).orElseThrow {
                CarNotFoundException()
            }

        if (car.ownerId != user.id) {
            throw InvalidRentalRequestException()
        }

        rental.status = updateRentalStatus(rentalRequestSubmitAction.action)

        return rentalRepository.save(rental)
    }

    private fun updateRentalStatus(action: RentalAction) =
        if (action == RentalAction.ACCEPT) RentalStatus.ACCEPTED else RentalStatus.DECLINED
}
