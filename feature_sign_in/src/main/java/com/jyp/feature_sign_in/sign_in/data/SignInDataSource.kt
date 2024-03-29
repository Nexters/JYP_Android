package com.jyp.feature_sign_in.sign_in.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.KakaoSignInResponse
import javax.inject.Inject


class SignInDataSource @Inject constructor(
    private val jypApi: JypApi
) {
    suspend fun signInWithKakao(): KakaoSignInResponse {
        return jypApi.signInWithKakao()
    }
}