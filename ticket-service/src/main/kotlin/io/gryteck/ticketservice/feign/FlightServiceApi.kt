package io.gryteck.ticketservice.feign

import io.gryteck.flight_service_api.FlightResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "\${app.service.flightService.name}", url = "\${app.service.gateway.baseUrl}/api/v1/flights")
interface FlightServiceApi {
    @GetMapping("/{flightNumber}")
    fun fetchByFlightNumber(@PathVariable flightNumber: String): Mono<ResponseEntity<Mono<FlightResponse>>>
}