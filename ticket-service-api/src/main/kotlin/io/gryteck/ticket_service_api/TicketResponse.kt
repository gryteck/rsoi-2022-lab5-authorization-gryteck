package io.gryteck.ticket_service_api

import io.gryteck.ticket_service_api.common.TicketStatus
import java.time.LocalDateTime
import java.util.*

data class TicketResponse(
    val ticketUid: UUID,
    val flightNumber: String,
    val fromAirport: String,
    val toAirport: String,
    val date: LocalDateTime,
    val price: Int,
    val status: TicketStatus
)
