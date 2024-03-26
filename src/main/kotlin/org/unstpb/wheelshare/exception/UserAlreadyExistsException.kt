package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ERR-0001: User already exists")
class UserAlreadyExistsException : RuntimeException()
