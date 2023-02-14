package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.User
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): ApiResult<User> {
        return apiResult {
            userRepository.getMe()
        }
    }
}
