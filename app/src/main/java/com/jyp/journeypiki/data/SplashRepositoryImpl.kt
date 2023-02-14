package com.jyp.journeypiki.data

import com.jyp.core_network.jyp.model.response.KakaoSignInResponse
import com.jyp.journeypiki.domain.SplashRepository
import javax.inject.Inject


class SplashRepositoryImpl @Inject constructor(
    private val splashDataSource: SplashDataSource
): SplashRepository {

    override suspend fun signInWithKakao(): KakaoSignInResponse {
        return splashDataSource.signInWithKakao()
    }
}