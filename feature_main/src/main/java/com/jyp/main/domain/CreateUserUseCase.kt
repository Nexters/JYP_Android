package com.jyp.main.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.User
import javax.inject.Inject


class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun createUserAccount(
        name: String,
        profileImagePath: String,
        personalityId: String,
    ): ApiResult<User> {
        return apiResult {
            userRepository.createUserAccount(name, profileImagePath, personalityId)
        }
    }
}