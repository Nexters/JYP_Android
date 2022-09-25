package com.jyp.feature_my_journey.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.response.UserResponse
import javax.inject.Inject

class UserDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun getUser(userId: String): UserResponse {
        return jypApi.getUser(userId)
    }
}
