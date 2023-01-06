package io.gryteck.ticketservice.domain

import io.gryteck.ticket_service_api.common.TicketStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("ticket")
data class TicketEntity(
    @field:Id
    @field:Column("id")
    val id: Int,
    @field:Column("ticket_uid")
    val ticketUid: UUID,
    @field:Column("username")
    val username: String,
    @field:Column("flight_number")
    val flightNumber: String,
    @field:Column("price")
    val price: Int,
    @field:Column("status")
    val status: TicketStatus
)
