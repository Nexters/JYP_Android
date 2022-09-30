package com.jyp.journeypiki.domain

import com.jyp.core.model.UserInfoResponseModel
import kotlinx.coroutines.flow.Flow


abstract class UserInfoRepositoryInterface {
    abstract fun checkUserJourneyPikiAccountExistence(
        userInfoHeaders: Map<String, String>,
        id: String
    ): Flow<Result<UserInfoResponseModel>>
}