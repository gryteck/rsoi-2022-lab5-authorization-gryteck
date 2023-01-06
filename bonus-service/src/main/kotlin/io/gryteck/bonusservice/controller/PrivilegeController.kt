package io.gryteck.bonusservice.controller

import io.gryteck.bonus_service_api.*
import io.gryteck.bonusservice.service.PrivilegeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/privilege")
class PrivilegeController(
    private val privilegeService: PrivilegeService
) {
    @GetMapping
    suspend fun getPrivilegeInfo(
        authentication: Authentication
    ): ResponseEntity<PrivilegeInfoResponse> =
        ResponseEntity.ok(privilegeService.findPrivilege(authentication.getPreferredUsername()))

    @PostMapping("/pay")
    suspend fun payWithBonuses(
        authentication: Authentication,
        @RequestBody request: PayWithBonusesRequest
    ): ResponseEntity<PayWithBonusesResponse> =
        ResponseEntity.ok(privilegeService.payWithBonuses(authentication.getPreferredUsername(), request))

    @PostMapping("/fill")
    suspend fun fillBonuses(
        authentication: Authentication,
        @RequestBody request: FillBonusesRequest
    ): ResponseEntity<FillBonusesResponse> =
        ResponseEntity.ok(privilegeService.fillBonuses(authentication.getPreferredUsername(), request))

    @PostMapping("/cancel")
    suspend fun cancelBonusOperation(
        authentication: Authentication,
        @RequestBody request: CancelBonusesRequest
    ): ResponseEntity<String> {
        privilegeService.cancelBonuses(authentication.getPreferredUsername(), request)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

private fun Authentication.getPreferredUsername(): String =
    (this.credentials as Jwt).claims["preferred_username"] as String
