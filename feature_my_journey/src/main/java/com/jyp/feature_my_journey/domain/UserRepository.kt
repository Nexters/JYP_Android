package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.response.UserResponse

interface UserRepository {
    suspend fun getUser(userId: String): UserResponse
}
