package com.jyp.feature_sign_in.sign_in.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.KakaoSignIn
import javax.inject.Inject


class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend fun signInWithKakao(
        token: String
    ): ApiResult<KakaoSignIn> {
        return apiResult {
            signInRepository.signInWithKakao(token)
        }
    }
}