package com.jyp.feature_sign_in.questions.data

import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.model.response.UserResponse
import javax.inject.Inject


class QuestionRepositoryImpl @Inject constructor(
    private val questionDataSource: QuestionDataSource
): QuestionRepository {

    override suspend fun createUserAccount(
        token: String,
        body: CreateUserRequestBody
    ): UserResponse {
        return questionDataSource.createUserAccount(token, body)
    }
}