package com.jyp.feature_my_journey.data

import com.jyp.core_network.jyp.model.response.UserResponse
import com.jyp.feature_my_journey.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUser(userId: String): UserResponse {
        return userDataSource.getUser(userId)
    }
}
