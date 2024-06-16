package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(
    code = HttpStatus.BAD_REQUEST,
    reason = """
    ERR-0011: Cannot submit review. You don't have a valid rent of this vehicle
    """,
)
class NoRentalToReviewException : RuntimeException()
