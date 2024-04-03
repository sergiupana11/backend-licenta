package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "ERR-0004: Rental offer not found")
class RentalNotFoundException : RuntimeException()
