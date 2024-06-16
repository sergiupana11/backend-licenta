package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "ERR-0012: The user already rented another car in that timeframe")
class OverlappingRentalsForUserException : RuntimeException()
