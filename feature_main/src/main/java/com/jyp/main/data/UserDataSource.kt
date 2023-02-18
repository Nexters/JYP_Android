package com.jyp.main.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.UserResponse
import javax.inject.Inject

class UserDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun getMe(): UserResponse {
        return jypApi.getMe()
    }
}
