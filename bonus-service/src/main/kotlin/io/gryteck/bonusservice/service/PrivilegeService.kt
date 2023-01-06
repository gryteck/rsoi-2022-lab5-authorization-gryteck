package io.gryteck.bonusservice.service

import io.gryteck.bonus_service_api.*

interface PrivilegeService {
    suspend fun findPrivilege(username: String): PrivilegeInfoResponse
    suspend fun payWithBonuses(username: String, request: PayWithBonusesRequest): PayWithBonusesResponse
    suspend fun fillBonuses(username: String, request: FillBonusesRequest): FillBonusesResponse
    suspend fun cancelBonuses(username: String, request: CancelBonusesRequest)
}