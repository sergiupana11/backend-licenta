package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "ERR-0024: User already has valid insurance")
class UserAlreadyHasValidInsuranceException : RuntimeException()
