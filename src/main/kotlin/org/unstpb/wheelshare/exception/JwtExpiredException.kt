package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ERR-0006: JWT Token expired")
class JwtExpiredException : RuntimeException()
