package com.jyp.journeypiki.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.KakaoSignIn
import javax.inject.Inject


class SplashUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    suspend fun signInWithKakao(
        token: String
    ): ApiResult<KakaoSignIn> {
        return apiResult {
            splashRepository.signInWithKakao(token)
        }
    }
}