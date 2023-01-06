package io.gryteck.userservice.feign

import io.gryteck.ticket_service_api.TicketResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "\${app.service.ticketService.name}", url = "\${app.service.gateway.baseUrl}/api/v1/tickets")
interface TicketServiceApi {
    @GetMapping
    fun getAllUserTickets(
        @RequestHeader(name = "X-User-Name") username: String
    ): Mono<ResponseEntity<Flux<TicketResponse>>>
}