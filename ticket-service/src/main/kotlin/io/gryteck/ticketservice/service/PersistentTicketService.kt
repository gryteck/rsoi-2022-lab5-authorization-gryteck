package io.gryteck.ticketservice.service

import io.gryteck.bonus_service_api.CancelBonusesRequest
import io.gryteck.bonus_service_api.FillBonusesRequest
import io.gryteck.bonus_service_api.PayWithBonusesRequest
import io.gryteck.bonus_service_api.common.PrivilegeShortInfo
import io.gryteck.common.exception.EntityNotFoundException
import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.ticket_service_api.TicketPurchaseRequest
import io.gryteck.ticket_service_api.TicketPurchaseResponse
import io.gryteck.ticket_service_api.TicketResponse
import io.gryteck.ticket_service_api.common.TicketStatus
import io.gryteck.ticketservice.domain.TicketEntity
import io.gryteck.ticketservice.mapper.toTicketResponse
import io.gryteck.ticketservice.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class PersistentTicketService(
    private val ticketRepository: TicketRepository,
    private val flightService: FlightService,
    private val bonusService: BonusService,
    private val retryService: RetryService
) : TicketService {
    @Transactional(readOnly = true)
    override fun findTickets(username: String): Flow<TicketResponse> = ticketRepository.findAllByUsername(username)
        .map {
            val flightResponse = getFlightWithFallback(it.flightNumber)
            it.toTicketResponse(flightResponse)
        }

    @Transactional(readOnly = true)
    override suspend fun findTicket(username: String, ticketUid: UUID): TicketResponse =
        ticketRepository.findFirstByUsernameAndTicketUid(username, ticketUid)?.let {
            val flightResponse = getFlightWithFallback(it.flightNumber)
            it.toTicketResponse(flightResponse)
        } ?: throw EntityNotFoundException("Ticket with username $username and uid $ticketUid not found")

    @Transactional
    override suspend fun buyTicket(username: String, request: TicketPurchaseRequest): TicketPurchaseResponse {
        // TODO add date validation handling and saga
        val flight = flightService.getByFlightNumber(request.flightNumber) // Check that flight exists
        val ticket = TicketEntity(
            id = 0,
            ticketUid = UUID.randomUUID(),
            username = username,
            flightNumber = request.flightNumber,
            price = request.price,
            status = TicketStatus.PAID
        )
        val savedTicket = ticketRepository.save(ticket)
        var paidFromBalance: Int = 0

        val privilege: PrivilegeShortInfo = if (request.paidFromBalance) {
            val payRequest = PayWithBonusesRequest(ticketUid = savedTicket.ticketUid, price = savedTicket.price)
            val response = bonusService.payWithBonuses(username, payRequest)
            paidFromBalance = response.payed
            response.privilege
        } else {
            val fillBonusesRequest = FillBonusesRequest(ticketUid = savedTicket.ticketUid, price = savedTicket.price)
            val response = bonusService.fillBonuses(username, fillBonusesRequest)
            response.privilege
        }

        return TicketPurchaseResponse(
            ticketUid = savedTicket.ticketUid,
            flightNumber = request.flightNumber,
            fromAirport = flight.fromAirport,
            toAirport = flight.toAirport,
            date = flight.date,
            status = savedTicket.status,
            price = savedTicket.price,
            paidByMoney = savedTicket.price - paidFromBalance,
            paidByBonuses = paidFromBalance,
            privilege = privilege
        )
    }

    @Transactional
    override suspend fun cancelTicket(username: String, ticketUid: UUID) {
        val ticket = ticketRepository.findFirstByUsernameAndTicketUid(username, ticketUid) ?:
            throw EntityNotFoundException("Ticket with username $username and uid $ticketUid not found")
        try {
            bonusService.cancelBonusOperation(username, CancelBonusesRequest(ticketUid))
        } catch (_: Exception) {
            retryService.queue.add(CancelBonusData(username, CancelBonusesRequest(ticketUid)))
        }

        ticketRepository.save(ticket.copy(status = TicketStatus.CANCELED))
    }

    private suspend fun getFlightWithFallback(flightNumber: String): FlightResponse {
        return try {
            flightService.getByFlightNumber(flightNumber)
        } catch (e: Exception) { // TODO add logging
            flightFallback
        }
    }
}

private val flightFallback = FlightResponse(
    flightNumber = "0000",
    fromAirport = "0000",
    toAirport = "00000",
    date = LocalDateTime.of(0, 0, 0, 0, 0),
    price = -1
)