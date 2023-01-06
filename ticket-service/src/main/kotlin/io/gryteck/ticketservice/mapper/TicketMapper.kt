package io.gryteck.ticketservice.mapper

import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.ticket_service_api.TicketResponse
import io.gryteck.ticketservice.domain.TicketEntity

fun TicketEntity.toTicketResponse(
    flightResponse: FlightResponse
): TicketResponse = TicketResponse(
    ticketUid = ticketUid,
    flightNumber = flightNumber,
    fromAirport = flightResponse.fromAirport,
    toAirport = flightResponse.toAirport,
    date = flightResponse.date,
    price = price,
    status = status
)
