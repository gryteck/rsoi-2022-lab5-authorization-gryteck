package io.gryteck.user_service_api

import io.gryteck.bonus_service_api.common.PrivilegeShortInfo
import io.gryteck.ticket_service_api.TicketResponse

data class UserInfoResponse(
    val tickets: List<TicketResponse>,
    val privilege: PrivilegeShortInfo?
)
