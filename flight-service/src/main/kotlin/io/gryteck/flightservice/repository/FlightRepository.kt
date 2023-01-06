package io.gryteck.flightservice.repository

import io.gryteck.flightservice.domain.FlightEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FlightRepository: CoroutineCrudRepository<FlightEntity, Int> {
    fun findBy(pageable: Pageable): Flow<FlightEntity>
    @Query("SELECT COUNT(*) FROM flight")
    suspend fun countAll(): Long
    suspend fun findFirstByFlightNumber(flightNumber: String): FlightEntity?
}

@Repository
class FlightRepositoryAdapter(private val flightRepository: FlightRepository) {
    suspend  fun findBy(pageable: Pageable): Page<FlightEntity> {
        val content = flightRepository.findBy(pageable)
            .toList()
        return PageImpl(content, pageable, flightRepository.countAll())
    }

    suspend fun findByFlightNumber(flightNumber: String): FlightEntity? {
        return flightRepository.findFirstByFlightNumber(flightNumber)
    }
}