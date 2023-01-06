package io.gryteck.gateway

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AuthorizationController {

    @GetMapping("/api/v1/authorize")
    fun getLoginPage(): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("/login"))
            .build()

}