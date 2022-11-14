package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.model.response.UserResponse

interface UserRepository {
    suspend fun getUser(userId: String): UserResponse
}
