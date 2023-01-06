package io.gryteck.bonusservice.repository

import io.gryteck.bonusservice.domain.PrivilegeHistoryEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PrivilegeHistoryRepository: CoroutineCrudRepository<PrivilegeHistoryEntity, Int> {
    fun findAllByPrivilegeId(privilegeId: Int): Flow<PrivilegeHistoryEntity>
    suspend fun findFirstByTicketUidAndPrivilegeId(ticketUid: UUID, privilegeId: Int): PrivilegeHistoryEntity?
}
