package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ERR-0030: Rental period is not fully included in the insurance period")
class InsurancePeriodDoesNotMatchRentalPeriodException : RuntimeException()
