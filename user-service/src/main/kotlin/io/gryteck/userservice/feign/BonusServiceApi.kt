package io.gryteck.userservice.feign

import io.gryteck.bonus_service_api.PrivilegeInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "\${app.service.bonusService.name}", url = "\${app.service.gateway.baseUrl}/api/v1/privilege")
interface BonusServiceApi {
    @GetMapping
    fun getPrivilegeInfo(
        @RequestHeader(name = "X-User-Name") username: String
    ): Mono<ResponseEntity<Mono<PrivilegeInfoResponse>>>
}