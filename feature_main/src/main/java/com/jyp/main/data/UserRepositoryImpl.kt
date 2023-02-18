package com.jyp.main.data

import com.jyp.core_network.jyp.model.response.UserResponse
import com.jyp.main.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getMe(): UserResponse {
        return userDataSource.getMe()
    }
}
