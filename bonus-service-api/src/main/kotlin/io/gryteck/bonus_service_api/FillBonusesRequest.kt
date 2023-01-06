package io.gryteck.bonus_service_api

import java.util.*

data class FillBonusesRequest(
    val ticketUid: UUID,
    val price: Int
)
