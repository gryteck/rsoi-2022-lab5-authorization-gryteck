package io.gryteck.flightservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("airport")
data class AirportEntity(
    @field:Id
    @field:Column("id")
    val id: Int,
    @field:Column("name")
    val name: String,
    @field:Column("city")
    val city: String,
    @field:Column("country")
    val country: String
)
