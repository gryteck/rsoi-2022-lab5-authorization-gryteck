package io.gryteck.bonus_service_api

import io.gryteck.bonus_service_api.common.PrivilegeShortInfo

data class PayWithBonusesResponse(
    val payed: Int,
    val privilege: PrivilegeShortInfo
)
