package com.jyp.main.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.model.request.SetProfileRequestBody
import com.jyp.core_network.jyp.model.response.UserResponse
import javax.inject.Inject

class UserDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun getMe(): UserResponse {
        return jypApi.getMe()
    }

    suspend fun createUserAccount(
        name: String,
        profileImagePath: String,
        personalityId: String,
    ): UserResponse {
        val body = CreateUserRequestBody(
            name,
            profileImagePath,
            personalityId,
        )

        return jypApi.createUserAccount(body)
    }

    suspend fun updateUserProfile(id: String, profileImagePath: String): UserResponse {
        val setProfileRequestBody = SetProfileRequestBody(profileImagePath)

        return jypApi.updateUserProfile(id, setProfileRequestBody)
    }
}
