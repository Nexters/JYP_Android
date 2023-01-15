package com.jyp.feature_sign_in.sign_in.data

import com.jyp.core_network.jyp.model.response.KakaoSignInResponse


interface SignInRepository {
    suspend fun signInWithKakao(): KakaoSignInResponse
}