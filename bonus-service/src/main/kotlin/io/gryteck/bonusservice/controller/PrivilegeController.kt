package io.gryteck.bonusservice.controller

import io.gryteck.bonus_service_api.*
import io.gryteck.bonusservice.service.PrivilegeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/privilege")
class PrivilegeController(
    private val privilegeService: PrivilegeService
) {
    @GetMapping
    suspend fun getPrivilegeInfo(
        @RequestHeader(name = "X-User-Name", required = true) username: String
    ): ResponseEntity<PrivilegeInfoResponse> = ResponseEntity.ok(privilegeService.findPrivilege(username))

    @PostMapping("/pay")
    suspend fun payWithBonuses(
        @RequestHeader(name = "X-User-Name", required = true) username: String,
        @RequestBody request: PayWithBonusesRequest
    ): ResponseEntity<PayWithBonusesResponse> =
        ResponseEntity.ok(privilegeService.payWithBonuses(username, request))

    @PostMapping("/fill")
    suspend fun fillBonuses(
        @RequestHeader(name = "X-User-Name", required = true) username: String,
        @RequestBody request: FillBonusesRequest
    ): ResponseEntity<FillBonusesResponse> =
        ResponseEntity.ok(privilegeService.fillBonuses(username, request))

    @PostMapping("/cancel")
    suspend fun cancelBonusOperation(
        @RequestHeader(name = "X-User-Name", required = true) username: String,
        @RequestBody request: CancelBonusesRequest
    ): ResponseEntity<String> {
        privilegeService.cancelBonuses(username, request)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}