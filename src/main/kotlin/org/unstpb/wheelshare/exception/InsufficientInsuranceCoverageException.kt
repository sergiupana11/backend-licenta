package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ERR-0015: Insurance level of coverage is too low")
class InsufficientInsuranceCoverageException : RuntimeException()
