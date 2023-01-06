package io.gryteck.bonus_service_api

import io.gryteck.bonus_service_api.common.BalanceHistory
import io.gryteck.bonus_service_api.common.PrivilegeStatus

data class PrivilegeInfoResponse(
    val balance: Int,
    val status: PrivilegeStatus,
    val history: List<BalanceHistory>
)
