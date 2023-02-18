package com.jyp.feature_sign_in.questions.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.User
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.feature_sign_in.questions.data.QuestionRepository
import javax.inject.Inject


class QuestionUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {
    suspend fun createUserAccount(
        body: CreateUserRequestBody
    ): ApiResult<User> {
        return apiResult {
            questionRepository.createUserAccount(body)
        }
    }
}