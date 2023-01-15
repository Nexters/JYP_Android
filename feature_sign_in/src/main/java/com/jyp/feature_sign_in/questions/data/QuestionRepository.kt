package com.jyp.feature_sign_in.questions.data

import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.model.response.UserResponse


interface QuestionRepository {
    suspend fun createUserAccount(body: CreateUserRequestBody): UserResponse
}