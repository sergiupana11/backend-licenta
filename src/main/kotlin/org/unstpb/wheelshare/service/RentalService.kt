package org.unstpb.wheelshare.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.unstpb.wheelshare.constants.Constants.DAYS_IN_YEAR
import org.unstpb.wheelshare.dto.NewRentalRequest
import org.unstpb.wheelshare.dto.RentalDto
import org.unstpb.wheelshare.dto.RentalIdWrapper
import org.unstpb.wheelshare.dto.RentalRequestSubmitAction
import org.unstpb.wheelshare.entity.Rental
import org.unstpb.wheelshare.entity.enums.RentalAction
import org.unstpb.wheelshare.entity.enums.RentalStatus
import org.unstpb.wheelshare.exception.*
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.InsuranceRepository
import org.unstpb.wheelshare.repository.RentalRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val insuranceRepository: InsuranceRepository,
    private val logger: Logger = LoggerFactory.getLogger(RentalService::class.java),
) {
    fun createRentalRequest(
        username: String,
        newRentalRequest: NewRentalRequest,
    ): RentalIdWrapper {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        if (
            newRentalRequest.startDate.isBefore(LocalDateTime.now()) ||
            newRentalRequest.endDate.isBefore(LocalDateTime.now()) ||
            newRentalRequest.endDate.isBefore(newRentalRequest.startDate) ||
            newRentalRequest.startDate.isAfter(LocalDateTime.now().plusDays(DAYS_IN_YEAR)) ||
            newRentalRequest.endDate.isAfter(LocalDateTime.now().plusDays(DAYS_IN_YEAR))
        ) {
            throw InvalidDateException()
        }

        logger.info("User with id: ${user.id}, made rental request for car with id: ${newRentalRequest.carId}")

        val car = carRepository.findById(newRentalRequest.carId).orElseThrow { CarNotFoundException() }

        if (car.ownerId == user.id) {
            throw CannotRentOwnCarException()
        }

        val insurance = insuranceRepository.findById(newRentalRequest.insuranceId).orElseThrow { InsuranceNotFoundException() }

        if (car.minimumInsuranceType > insurance.insuranceType) {
            throw InsufficientInsuranceCoverageException()
        }
        logger.info("Owner id: ${car.ownerId}")

        val isCarAvailableForRent = !checkOverlappingDates(rentalRepository.findAllByCarId(car.id), newRentalRequest)

        if (!isCarAvailableForRent) {
            throw CarRentingPeriodOverlapsException()
        }

        val userHasOverlappingRentals = checkOverlappingDates(rentalRepository.findAllByRenterId(user.id), newRentalRequest)

        if (userHasOverlappingRentals) {
            throw OverlappingRentalsForUserException()
        }

        val rental =
            Rental(
                UUID.randomUUID(),
                newRentalRequest.carId,
                user.id,
                newRentalRequest.startDate,
                newRentalRequest.endDate,
                RentalStatus.PENDING,
            )

        rentalRepository.save(rental)
        return RentalIdWrapper(rental.id)
    }

    fun getAllRentalsForUser(username: String): Map<String, List<RentalDto>> {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val outgoingRequests =
            rentalRepository.findAllByRenterId(user.id).map {
                logger.info("found rental: $it")
                val car = carRepository.findById(it.carId).orElseThrow { CarNotFoundException() }
                val owner = userRepository.findById(car.ownerId).orElseThrow { UserNotFoundException() }

                RentalDto.of(it, car, renter = user, owner)
            }

        val currentOwnerCars = carRepository.findAllByOwnerId(user.id)

        val incomingRequests =
            currentOwnerCars.flatMap { car ->
                rentalRepository.findAllByCarId(car.id).map { rental ->
                    val renter = userRepository.findById(rental.renterId).orElseThrow { UserNotFoundException() }

                    RentalDto.of(rental, car, renter, owner = user)
                }
            }

        return mapOf("incoming" to incomingRequests, "outgoing" to outgoingRequests)
    }

    fun submitRentalAction(
        username: String,
        rentalRequestSubmitAction: RentalRequestSubmitAction,
    ): Rental {
        logger.info("Received request")
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val rental =
            rentalRepository.findById(rentalRequestSubmitAction.rentalId).orElseThrow {
                RentalNotFoundException()
            }

        val car =
            carRepository.findById(rental.carId).orElseThrow {
                CarNotFoundException()
            }

        val isOwner = user.id == car.ownerId

        logger.info("checking if the rental period is before the current date")
        // Check if the rental period is before the current date
        val currentDate = LocalDateTime.now()
        if (rental.startDate.isBefore(currentDate)) {
            throw InvalidActionException()
        }

        logger.info("checking if  rental overlap if accepting the request")
        // Check for rental overlap if accepting the request
        if (rentalRequestSubmitAction.action == RentalAction.ACCEPT) {
            val overlappingRentals =
                rentalRepository.findAllByCarId(car.id)
                    .filter {
                        it.status == RentalStatus.ACCEPTED &&
                            it.endDate.isAfter(
                                rental.startDate,
                            ) && it.startDate.isBefore(rental.endDate)
                    }

            if (overlappingRentals.isNotEmpty()) {
                throw OverlappingRentalsForUserException()
            }
        }

        if (rentalRequestSubmitAction.action in listOf(RentalAction.ACCEPT, RentalAction.DECLINE) && !isOwner) {
            throw NoPermissionForActionException()
        }

        rental.status = updateRentalStatus(rentalRequestSubmitAction.action)

        // Decline overlapping requests if one is accepted
        if (rentalRequestSubmitAction.action == RentalAction.ACCEPT) {
            val overlappingRequests =
                rentalRepository.findAllByCarId(car.id)
                    .filter {
                        it.status == RentalStatus.PENDING &&
                            it.endDate.isAfter(
                                rental.startDate,
                            ) && it.startDate.isBefore(rental.endDate)
                    }

            overlappingRequests.forEach {
                it.status = RentalStatus.DECLINED
                rentalRepository.save(it)
            }
        }

        return rentalRepository.save(rental)
    }

    private fun updateRentalStatus(action: RentalAction) =
        when (action) {
            RentalAction.ACCEPT -> RentalStatus.ACCEPTED
            RentalAction.DECLINE -> RentalStatus.DECLINED
            RentalAction.CANCEL -> RentalStatus.CANCELLED
        }

    private fun checkOverlappingDates(
        list: List<Rental>,
        newRentalRequest: NewRentalRequest,
    ) = list.filter {
        it.status == RentalStatus.ACCEPTED
    }.sortedBy {
        it.endDate
    }.dropWhile {
        it.endDate.isBefore(newRentalRequest.startDate)
    }.any {
        newRentalRequest.startDate.isBefore(it.endDate) &&
            it.startDate.isBefore(newRentalRequest.endDate)
    }
}
