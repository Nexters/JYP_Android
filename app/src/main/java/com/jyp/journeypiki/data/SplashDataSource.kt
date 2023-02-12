package com.jyp.journeypiki.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.KakaoSignInResponse
import javax.inject.Inject


class SplashDataSource @Inject constructor(
    private val jypApi: JypApi
) {
    suspend fun signInWithKakao(): KakaoSignInResponse {
        return jypApi.signInWithKakao()
    }
}