package io.gryteck.common

import io.gryteck.common.exception.ClientResponseException
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Suppress("UNCHECKED_CAST")
fun <T> ResponseEntity<T>.safeBody(): T {
    if (this.statusCode.isError || !this.hasBody()) {
        throw ClientResponseException(response = this)
    }

    return this.body as T
}

fun <T> Mono<ResponseEntity<Mono<T>>>.safeBody(): Mono<T> {
    return this.flatMap {
        it.safeBody()
    }
}

fun <T> Mono<ResponseEntity<Flux<T>>>.safeBody(): Flux<T> {
    return this.flatMapMany {
        it.safeBody()
    }
}
