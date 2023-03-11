package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.request.SetProfileRequestBody
import com.jyp.core_network.jyp.model.response.UserResponse
import javax.inject.Inject

class UserDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun getMe(): UserResponse {
        return jypApi.getMe()
    }

    suspend fun updateUserProfile(id: String, profileImagePath: String): UserResponse {
        val setProfileRequestBody = SetProfileRequestBody(profileImagePath)

        return jypApi.updateUserProfile(id, setProfileRequestBody)
    }
}
