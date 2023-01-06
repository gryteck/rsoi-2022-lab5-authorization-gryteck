package io.gryteck.ticketservice.service

import io.gryteck.flight_service_api.FlightResponse

interface FlightService {
    suspend fun getByFlightNumber(flightNumber: String): FlightResponse
}