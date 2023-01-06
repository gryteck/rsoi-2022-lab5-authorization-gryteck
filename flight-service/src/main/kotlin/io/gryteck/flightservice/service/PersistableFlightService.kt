package io.gryteck.flightservice.service

import io.gryteck.common.exception.EntityNotFoundException
import io.gryteck.flight_service_api.FlightResponse
import io.gryteck.flightservice.PageResponse
import io.gryteck.flightservice.domain.AirportEntity
import io.gryteck.flightservice.mapper.toFlightResponse
import io.gryteck.flightservice.repository.AirportRepository
import io.gryteck.flightservice.repository.FlightRepositoryAdapter
import io.gryteck.flightservice.toPageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersistableFlightService(
    private val flightRepository: FlightRepositoryAdapter,
    private val airportRepository: AirportRepository
): FlightService {
    @Transactional
    override suspend fun findFlights(page: Int, size: Int): PageResponse<FlightResponse> = flightRepository
        .findBy(PageRequest.of(page, size))
        .suspendableMap { flightEntity ->
            val fromAirport = findAirport(flightEntity.fromAirportId)
            val toAirport = findAirport(flightEntity.toAirportId)

            flightEntity.toFlightResponse(fromAirport, toAirport)
        }.toPageResponse()

    @Transactional(readOnly = true)
    override suspend fun findFlight(flightNumber: String): FlightResponse = flightRepository.findByFlightNumber(flightNumber)?.let {
        val fromAirport = findAirport(it.fromAirportId)
        val toAirport = findAirport(it.toAirportId)

        it.toFlightResponse(fromAirport, toAirport)
    } ?: throw EntityNotFoundException("Flight with flightNumber '$flightNumber' not found")

    private suspend fun findAirport(id: Int): AirportEntity = airportRepository.findById(id)
        ?: throw EntityNotFoundException("Airport with id '$id' not found")
}

inline fun <U, T> Page<U>.suspendableMap(converter: (U) -> T): Page<T> {
    val newContent = content.map {
        converter(it)
    }

    return PageImpl(newContent, pageable, totalElements)
}