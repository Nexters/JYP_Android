package com.jyp.main.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.User
import javax.inject.Inject

class SetMyProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(profileImagePath: String): ApiResult<User> {
        return apiResult {
            userRepository.updateUserProfile(userRepository.getMe().data.id, profileImagePath)
        }
    }
}
