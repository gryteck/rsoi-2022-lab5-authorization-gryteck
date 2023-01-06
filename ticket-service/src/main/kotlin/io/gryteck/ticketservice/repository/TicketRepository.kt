package io.gryteck.ticketservice.repository

import io.gryteck.ticketservice.domain.TicketEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TicketRepository: CoroutineCrudRepository<TicketEntity, Int> {
    fun findAllByUsername(username: String): Flow<TicketEntity>
    suspend fun findFirstByUsernameAndTicketUid(username: String, ticketUid: UUID): TicketEntity?
}