package io.gryteck.flightservice.mapper

import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.flightservice.domain.AirportEntity
import io.gryteck.flightservice.domain.FlightEntity

fun FlightEntity.toFlightResponse(fromAirport: AirportEntity, toAirport: AirportEntity) = FlightResponse(
    flightNumber = flightNumber,
    fromAirport = "${fromAirport.city} ${fromAirport.name}",
    toAirport = "${toAirport.city} ${toAirport.name}",
    date = dateTime,
    price = price
)