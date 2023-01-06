package io.gryteck.ticketservice.service

import io.gryteck.common.safeBody
import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.ticketservice.feign.FlightServiceApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service

@Service
class NetworkFlightService(private val flightServiceApi: FlightServiceApi): FlightService {
    override suspend fun getByFlightNumber(flightNumber: String): FlightResponse = flightServiceApi
        .fetchByFlightNumber(flightNumber).safeBody().asFlow().first()
}