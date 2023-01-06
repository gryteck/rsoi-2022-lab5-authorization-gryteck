package io.gryteck.bonusservice.mapper

import io.gryteck.bonus_service_api.PrivilegeInfoResponse
import io.gryteck.bonus_service_api.common.BalanceHistory
import io.gryteck.bonus_service_api.common.PrivilegeShortInfo
import io.gryteck.bonusservice.domain.PrivilegeEntity

fun PrivilegeEntity.toPrivilegeInfoResponse(history: List<BalanceHistory>) = PrivilegeInfoResponse(
    balance = balance,
    status = status,
    history = history
)

fun PrivilegeEntity.toPrivilegeShortInfo() = PrivilegeShortInfo(
    balance = balance,
    status = status
)
