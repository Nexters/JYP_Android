package com.jyp.feature_sign_in.questions.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.model.response.UserResponse
import javax.inject.Inject


class QuestionDataSource @Inject constructor(
    private val jypApi: JypApi
) {
    suspend fun createUserAccount(body: CreateUserRequestBody): UserResponse {
        return jypApi.createUserAccount(body)
    }
}