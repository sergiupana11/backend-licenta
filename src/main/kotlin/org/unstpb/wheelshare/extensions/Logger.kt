package org.unstpb.wheelshare.extensions

import org.slf4j.Logger
import org.unstpb.wheelshare.constants.JWT_EXPIRED
import org.unstpb.wheelshare.exception.JwtExpiredException

fun <T> Logger.runCatchingExceptions(block: () -> T): T {
    try {
        return block()
    } catch (e: JwtExpiredException) {
        this.error(JWT_EXPIRED)
        throw e
    }
}
