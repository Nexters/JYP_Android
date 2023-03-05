package com.jyp.main.domain

import com.jyp.core_network.jyp.model.response.UserResponse

interface UserRepository {
    suspend fun getMe(): UserResponse
    suspend fun updateUserProfile(id: String, profileImagePath: String): UserResponse
}
