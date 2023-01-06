package io.gryteck.flightservice.service

import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.flightservice.PageResponse

interface FlightService {
    suspend fun findFlights(page: Int, size: Int): PageResponse<FlightResponse>
    suspend fun findFlight(flightNumber: String): FlightResponse
}