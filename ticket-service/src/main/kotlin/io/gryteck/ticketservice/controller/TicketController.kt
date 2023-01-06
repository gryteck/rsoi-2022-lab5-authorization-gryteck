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
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt

@RestController
@RequestMapping("/tickets")
class TicketController(private val ticketService: TicketService) {

    @GetMapping
    suspend fun getAllUserTickets(
        authentication: Authentication
    ): ResponseEntity<Flow<TicketResponse>> {
        return ResponseEntity.ok(ticketService.findTickets(authentication.getPreferredUsername()))
    }

    @GetMapping("/{ticketUid}")
    suspend fun getUserTicket(
        @PathVariable ticketUid: UUID,
        authentication: Authentication
    ): ResponseEntity<TicketResponse> {
        return ResponseEntity.ok(ticketService.findTicket(authentication.getPreferredUsername(), ticketUid))
    }

    @PostMapping
    suspend fun buyTicket(
        authentication: Authentication,
        @RequestBody ticketPurchaseRequest: TicketPurchaseRequest
    ): ResponseEntity<TicketPurchaseResponse> {
        return ResponseEntity.ok(ticketService.buyTicket(authentication.getPreferredUsername(), ticketPurchaseRequest))
    }

    @DeleteMapping("/{ticketUid}")
    suspend fun cancelTicket(
        authentication: Authentication,
        @PathVariable ticketUid: UUID
    ): ResponseEntity<String> {
        ticketService.cancelTicket(authentication.getPreferredUsername(), ticketUid)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

private fun Authentication.getPreferredUsername(): String =
    (this.credentials as Jwt).claims["preferred_username"] as String
