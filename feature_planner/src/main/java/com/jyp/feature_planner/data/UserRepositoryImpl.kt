package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.model.response.UserResponse
import com.jyp.feature_planner.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getMe(): UserResponse {
        return userDataSource.getMe()
    }
}
