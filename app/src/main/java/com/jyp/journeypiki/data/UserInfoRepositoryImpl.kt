package com.jyp.journeypiki.data

import com.jyp.core.UserInfoApi
import com.jyp.core.di.JourneyPikiRetrofit
import com.jyp.core.model.UserInfoResponseModel
import com.jyp.journeypiki.domain.UserInfoRepositoryInterface
import com.jyp.journeypiki.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserInfoRepositoryImpl @Inject constructor(
    @JourneyPikiRetrofit private val userInfoApi: UserInfoApi
) : UserInfoRepositoryInterface() {

    override fun checkUserJourneyPikiAccountExistence(
        userInfoHeaders: Map<String, String>,
        id: String
    ): Flow<Result<UserInfoResponseModel>> = flow {
        emit(
            resultOf { userInfoApi.getUserInfo(userInfoHeaders, id) }
        )
    }
}