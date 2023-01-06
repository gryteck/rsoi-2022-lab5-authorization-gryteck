package io.gryteck.flightservice.repository

import io.gryteck.flightservice.domain.AirportEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AirportRepository: CoroutineCrudRepository<AirportEntity, Int>