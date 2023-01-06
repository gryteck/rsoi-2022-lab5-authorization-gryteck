package io.gryteck.bonus_service_api.common

import java.time.LocalDateTime
import java.util.*

data class BalanceHistory(
    val date: LocalDateTime,
    val balanceDiff: Int,
    val ticketUid: UUID,
    val operationType: OperationType
)
