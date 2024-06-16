package org.unstpb.wheelshare.service

import org.springframework.stereotype.Service
import org.unstpb.wheelshare.dto.CarReviewDto
import org.unstpb.wheelshare.dto.CreateReviewDto
import org.unstpb.wheelshare.dto.ReviewData
import org.unstpb.wheelshare.dto.ReviewIdWrapper
import org.unstpb.wheelshare.entity.Review
import org.unstpb.wheelshare.entity.enums.RentalStatus
import org.unstpb.wheelshare.exception.*
import org.unstpb.wheelshare.repository.CarRepository
import org.unstpb.wheelshare.repository.RentalRepository
import org.unstpb.wheelshare.repository.ReviewRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.time.Instant
import java.util.*

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository,
) {
    fun createReview(
        username: String,
        createReviewDto: CreateReviewDto,
    ): ReviewIdWrapper {
        val poster = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        val rental = rentalRepository.findById(createReviewDto.rentalId).orElseThrow { RentalNotFoundException() }

        if (rental.status != RentalStatus.COMPLETED) {
            throw NoRentalToReviewException()
        }

        val car = carRepository.findById(rental.carId).orElseThrow { CarNotFoundException() }

        val owner = userRepository.findById(car.ownerId).orElseThrow { UserNotFoundException() }

        if (owner.id == poster.id) {
            throw PosterSameAsOwnerException()
        }

        Review(
            UUID.randomUUID(),
            rental.id,
            poster.id,
            car.id,
            createReviewDto.message,
            createReviewDto.rating,
            Date.from(Instant.now()),
        ).let {
            reviewRepository.save(it)
            return ReviewIdWrapper(it.id)
        }
    }

    fun getReviewsForCar(carId: UUID): CarReviewDto {
        val car = carRepository.findById(carId).orElseThrow { CarNotFoundException() }
        var cumulatedRating = 0.0

        reviewRepository.findAllByCarId(carId).map {
            val poster = userRepository.findById(it.posterId).get()
            cumulatedRating += it.rating

            ReviewData(
                it.id,
                it.posterId,
                poster.fullName(),
                car.id,
                car.fullName(),
                it.text,
                it.rating,
                it.dateCreated,
            )
        }.also {
            val averageRating = cumulatedRating / it.count()
            return CarReviewDto(it, averageRating)
        }
    }

    fun getReviewsPostedByMe(extractUsername: String): List<ReviewData> {
        val poster = userRepository.findByEmail(extractUsername) ?: throw UserNotFoundException()

        return reviewRepository.findAllByPosterId(poster.id).map {
            val car = carRepository.findById(it.carId).orElseThrow { CarNotFoundException() }

            ReviewData(
                it.id,
                poster.id,
                poster.fullName(),
                car.id,
                car.fullName(),
                it.text,
                it.rating,
                it.dateCreated,
            )
        }
    }
}
