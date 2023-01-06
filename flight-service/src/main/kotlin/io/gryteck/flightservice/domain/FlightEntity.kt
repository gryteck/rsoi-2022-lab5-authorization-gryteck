package io.gryteck.flightservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("flight")
data class FlightEntity(
    @field:Id
    @field:Column("id")
    val id: Int,
    @field:Column("flight_number")
    val flightNumber: String,
    @field:Column("datetime")
    val dateTime: LocalDateTime,
    @field:Column("from_airport_id")
    val fromAirportId: Int,
    @field:Column("to_airport_id")
    val toAirportId: Int,
    @field:Column("price")
    val price: Int
)
