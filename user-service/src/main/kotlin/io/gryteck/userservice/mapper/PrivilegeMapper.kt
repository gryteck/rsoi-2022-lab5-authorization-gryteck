package io.gryteck.userservice.mapper

import io.gryteck.bonus_service_api.PrivilegeInfoResponse
import io.gryteck.bonus_service_api.common.PrivilegeShortInfo

fun PrivilegeInfoResponse.toPrivilegeShortInfo() = PrivilegeShortInfo(
    balance = balance,
    status = status
)