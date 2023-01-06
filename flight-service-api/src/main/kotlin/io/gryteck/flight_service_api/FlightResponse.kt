package io.gryteck.flight_service_api

import java.time.LocalDateTime

data class FlightResponse(
    val flightNumber: String,
    val fromAirport: String,
    val toAirport: String,
    val date: LocalDateTime,
    val price: Int
)
