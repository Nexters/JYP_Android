package com.jyp.journeypiki.domain

import com.jyp.core_network.jyp.model.response.KakaoSignInResponse


interface SplashRepository {
    suspend fun signInWithKakao(): KakaoSignInResponse
}