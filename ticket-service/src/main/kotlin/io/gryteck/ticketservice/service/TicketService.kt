package io.gryteck.ticketservice.service

import io.gryteck.ticket_service_api.TicketPurchaseRequest
import io.gryteck.ticket_service_api.TicketPurchaseResponse
import io.gryteck.ticket_service_api.TicketResponse
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TicketService {
    fun findTickets(username: String): Flow<TicketResponse>
    suspend fun findTicket(username: String, ticketUid: UUID): TicketResponse
    suspend fun buyTicket(username: String, request: TicketPurchaseRequest): TicketPurchaseResponse
    suspend fun cancelTicket(username: String, ticketUid: UUID)
}