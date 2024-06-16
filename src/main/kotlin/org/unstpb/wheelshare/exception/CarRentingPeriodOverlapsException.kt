package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "ERR-0008: Car is already rented in that timeframe")
class CarRentingPeriodOverlapsException : RuntimeException()
