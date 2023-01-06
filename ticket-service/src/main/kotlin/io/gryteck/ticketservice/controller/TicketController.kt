package io.gryteck.ticketservice.controller

import io.gryteck.ticket_service_api.TicketPurchaseRequest
import io.gryteck.ticket_service_api.TicketPurchaseResponse
import io.gryteck.ticket_service_api.TicketResponse
import io.gryteck.ticketservice.service.TicketService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/tickets")
class TicketController(private val ticketService: TicketService) {

    @GetMapping
    suspend fun getAllUserTickets(
        @RequestHeader(name = "X-User-Name", required = true) username: String
    ): ResponseEntity<Flow<TicketResponse>> {
        return ResponseEntity.ok(ticketService.findTickets(username))
    }

    @GetMapping("/{ticketUid}")
    suspend fun getUserTicket(
        @PathVariable ticketUid: UUID,
        @RequestHeader(name = "X-User-Name", required = true) username: String
    ): ResponseEntity<TicketResponse> {
        return ResponseEntity.ok(ticketService.findTicket(username, ticketUid))
    }

    @PostMapping
    suspend fun buyTicket(
        @RequestHeader(name = "X-User-Name", required = true) username: String,
        @RequestBody ticketPurchaseRequest: TicketPurchaseRequest
    ): ResponseEntity<TicketPurchaseResponse> {
        return ResponseEntity.ok(ticketService.buyTicket(username, ticketPurchaseRequest))
    }

    @DeleteMapping("/{ticketUid}")
    suspend fun cancelTicket(
        @RequestHeader(name = "X-User-Name", required = true) username: String,
        @PathVariable ticketUid: UUID
    ): ResponseEntity<String> {
        ticketService.cancelTicket(username, ticketUid)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}