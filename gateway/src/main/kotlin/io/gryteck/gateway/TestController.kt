package io.gryteck.gateway

import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.WebSession
import java.security.Principal

@RestController
class TestController {
    @GetMapping("/me")
    fun getMe(principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(principal.name)
    @GetMapping("/token")
    fun getToken(@RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient): ResponseEntity<String> =
        ResponseEntity.ok(authorizedClient.accessToken.tokenValue)
    @GetMapping("/session")
    fun getSessionId(session: WebSession): ResponseEntity<String> =
        ResponseEntity.ok(session.id)
}