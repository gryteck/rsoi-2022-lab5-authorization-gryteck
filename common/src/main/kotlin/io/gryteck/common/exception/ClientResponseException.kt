package io.gryteck.common.exception

import org.springframework.http.ResponseEntity

class ClientResponseException : RuntimeException {
    val response: ResponseEntity<*>

    constructor(response: ResponseEntity<*>) : super() {
        this.response = response
    }

    constructor(message: String, response: ResponseEntity<*>) : super(message) {
        this.response = response
    }

    constructor(message: String, response: ResponseEntity<*>, cause: Throwable) : super(message, cause) {
        this.response = response
    }

    constructor(response: ResponseEntity<*>, cause: Throwable) : super(cause) {
        this.response = response
    }

    constructor(
        message: String,
        response: ResponseEntity<*>,
        cause: Throwable,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        this.response = response
    }
}