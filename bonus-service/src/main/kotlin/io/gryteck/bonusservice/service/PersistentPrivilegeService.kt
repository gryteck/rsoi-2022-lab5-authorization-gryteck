package io.gryteck.bonusservice.service

import io.gryteck.bonus_service_api.*
import io.gryteck.bonus_service_api.common.OperationType
import io.gryteck.bonusservice.domain.PrivilegeHistoryEntity
import io.gryteck.bonusservice.mapper.toBalanceHistory
import io.gryteck.bonusservice.mapper.toPrivilegeInfoResponse
import io.gryteck.bonusservice.mapper.toPrivilegeShortInfo
import io.gryteck.bonusservice.repository.PrivilegeHistoryRepository
import io.gryteck.bonusservice.repository.PrivilegeRepository
import io.gryteck.common.exception.EntityNotFoundException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.math.max

@Service
class PersistentPrivilegeService(
    private val privilegeRepository: PrivilegeRepository,
    private val privilegeHistoryRepository: PrivilegeHistoryRepository
): PrivilegeService {
    @Transactional
    override suspend fun findPrivilege(username: String): PrivilegeInfoResponse {
        val privilege = privilegeRepository.findFirstByUsername(username)
            ?: throw EntityNotFoundException("Privilege for username '$username' not found")

        val history = privilegeHistoryRepository.findAllByPrivilegeId(privilege.id)
            .map { it.toBalanceHistory() }.toList()

        return privilege.toPrivilegeInfoResponse(history)
    }

    @Transactional
    override suspend fun payWithBonuses(username: String, request: PayWithBonusesRequest): PayWithBonusesResponse {
        val privilege = privilegeRepository.findFirstByUsername(username) ?:
            throw EntityNotFoundException("Privilege for username '$username' not found")
        val balanceDiff = abs(request.price - privilege.balance)
        val savedPrivilege = privilegeRepository.save(privilege.copy(balance = privilege.balance - balanceDiff))

        val history = PrivilegeHistoryEntity(
            id = 0,
            privilegeId = savedPrivilege.id,
            ticketUid = request.ticketUid,
            dateTime = LocalDateTime.now(),
            balanceDiff = balanceDiff,
            operationType = OperationType.DEBIT_THE_ACCOUNT
        )
        privilegeHistoryRepository.save(history)

        return PayWithBonusesResponse(payed = balanceDiff, privilege = savedPrivilege.toPrivilegeShortInfo())
    }

    @Transactional
    override suspend fun fillBonuses(username: String, request: FillBonusesRequest): FillBonusesResponse {
        val privilege = privilegeRepository.findFirstByUsername(username) ?:
            throw EntityNotFoundException("Privilege for username '$username' not found")

        val balanceDiff = request.price / 10
        val savedPrivilege = privilegeRepository.save(privilege.copy(balance = privilege.balance + balanceDiff))

        val history = PrivilegeHistoryEntity(
            id = 0,
            privilegeId = savedPrivilege.id,
            ticketUid = request.ticketUid,
            dateTime = LocalDateTime.now(),
            balanceDiff = balanceDiff,
            operationType = OperationType.FILL_IN_BALANCE
        )
        privilegeHistoryRepository.save(history)

        return FillBonusesResponse(balanceDiff, savedPrivilege.toPrivilegeShortInfo())
    }

    @Transactional
    override suspend fun cancelBonuses(username: String, request: CancelBonusesRequest) {
        val privilege = privilegeRepository.findFirstByUsername(username) ?:
            throw EntityNotFoundException("Privilege for username '$username' not found")
        val history = privilegeHistoryRepository.findFirstByTicketUidAndPrivilegeId(request.ticketUid, privilege.id) ?:
            throw EntityNotFoundException("Privilege history with ticketUid '${request.ticketUid}' and privilegeId '${privilege.id}' not found")

        val balance = if (history.operationType == OperationType.DEBIT_THE_ACCOUNT) {
            privilege.balance + history.balanceDiff
        } else {
            max(privilege.balance - history.balanceDiff, 0)
        }

        privilegeRepository.save(privilege.copy(balance = balance))
    }
}