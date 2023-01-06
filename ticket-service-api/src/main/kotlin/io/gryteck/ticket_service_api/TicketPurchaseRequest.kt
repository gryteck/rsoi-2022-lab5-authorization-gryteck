package io.gryteck.ticket_service_api

data class TicketPurchaseRequest(
    val flightNumber: String,
    val price: Int,
    val paidFromBalance: Boolean
)
