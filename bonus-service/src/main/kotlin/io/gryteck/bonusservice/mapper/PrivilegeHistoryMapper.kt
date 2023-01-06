package io.gryteck.bonusservice.mapper

import io.gryteck.bonus_service_api.common.BalanceHistory
import io.gryteck.bonusservice.domain.PrivilegeHistoryEntity

fun PrivilegeHistoryEntity.toBalanceHistory() = BalanceHistory(
    date = dateTime,
    balanceDiff = balanceDiff,
    ticketUid = ticketUid,
    operationType = operationType
)