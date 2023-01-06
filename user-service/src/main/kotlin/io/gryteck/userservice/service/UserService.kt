package io.gryteck.userservice.service

import io.gryteck.user_service_api.UserInfoResponse

interface UserService {
    suspend fun getUserInfo(username: String): UserInfoResponse
}