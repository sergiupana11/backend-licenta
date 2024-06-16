package org.unstpb.wheelshare.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "ERR-0020: Not enough permissions to perform action")
class NotEnoughPrivilegesException : RuntimeException()
