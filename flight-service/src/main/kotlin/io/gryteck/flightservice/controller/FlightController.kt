package io.gryteck.flightservice.controller

import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.flightservice.PageResponse
import io.gryteck.flightservice.service.FlightService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightService: FlightService
) {
    @GetMapping
    suspend fun getFlights(
        @RequestParam(required = false) page: Int = 1,
        @RequestParam(required = false) size: Int = Int.MAX_VALUE
    ): ResponseEntity<PageResponse<FlightResponse>> = ResponseEntity.ok(flightService.findFlights(page - 1, size))

    @GetMapping("/{flightNumber}")
    suspend fun getFlightByNumber(
        @PathVariable flightNumber: String
    ): ResponseEntity<FlightResponse> = ResponseEntity.ok(flightService.findFlight(flightNumber))
}