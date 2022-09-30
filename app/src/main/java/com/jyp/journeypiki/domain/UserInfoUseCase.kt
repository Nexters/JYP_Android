package com.jyp.journeypiki.domain

import com.jyp.core.model.UserInfoResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserInfoUseCase @Inject constructor(
    private val userInfoRepositoryInterface: UserInfoRepositoryInterface
) {
    fun checkUserJourneyPikiAccountExistence(
        userInfoHeaders: Map<String, String>,
        id: String
    ): Flow<Result<UserInfoResponseModel>> {
        return userInfoRepositoryInterface.checkUserJourneyPikiAccountExistence(
            userInfoHeaders,
            id
        )
    }
}