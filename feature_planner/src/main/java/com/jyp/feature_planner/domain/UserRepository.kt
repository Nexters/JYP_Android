package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.response.UserResponse

interface UserRepository {
    suspend fun getMe(): UserResponse
}
