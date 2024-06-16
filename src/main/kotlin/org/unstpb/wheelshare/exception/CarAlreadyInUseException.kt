package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = """
    ERR-0010: Cannot delete car, since it is rented. Cancel the rentals or wait for them to end
""")
class CarAlreadyInUseException: RuntimeException()