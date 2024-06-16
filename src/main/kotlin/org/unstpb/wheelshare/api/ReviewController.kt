package org.unstpb.wheelshare.api

import org.springframework.web.bind.annotation.*
import org.unstpb.wheelshare.dto.CreateReviewDto
import org.unstpb.wheelshare.service.JwtService
import org.unstpb.wheelshare.service.ReviewService
import org.unstpb.wheelshare.utils.AUTHORIZATION_HEADER
import java.util.UUID

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000"], maxAge = 3600)
class ReviewController(
    private val reviewService: ReviewService,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createReview(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
        createReviewDto: CreateReviewDto,
    ) = reviewService.createReview(jwtService.extractUsername(jwt), createReviewDto)

    @GetMapping("/cars/{carId}")
    fun getReviewsForCar(
        @PathVariable carId: UUID,
    ) = reviewService.getReviewsForCar(carId)

    @GetMapping("/me")
    fun getReviewsPostedByMe(
        @RequestHeader(AUTHORIZATION_HEADER) jwt: String,
    ) = reviewService.getReviewsPostedByMe(jwtService.extractUsername(jwt))
}
