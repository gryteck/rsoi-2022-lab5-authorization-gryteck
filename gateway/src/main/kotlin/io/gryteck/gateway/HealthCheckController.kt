package io.gryteck.gateway

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/")
    fun healthCheckBasePath(): ResponseEntity<String> = ResponseEntity.ok("UP")
}