package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ERR-0017: Invalid insurance duration. Must be at least one week")
class InvalidInsuranceDurationException : RuntimeException()
