package io.gryteck.userservice.controller

import io.gryteck.user_service_api.UserInfoResponse
import io.gryteck.userservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/me")
    suspend fun getUserInfo(
        authentication: Authentication
    ): ResponseEntity<UserInfoResponse> = ResponseEntity.ok(userService.getUserInfo(authentication.getPreferredUsername()))
}

private fun Authentication.getPreferredUsername(): String =
    (this.credentials as Jwt).claims["preferred_username"] as String
