package com.jyp.feature_sign_in.sign_in.data

import com.jyp.core_network.jyp.model.response.KakaoSignInResponse
import javax.inject.Inject


class SignInRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource
): SignInRepository {

    override suspend fun signInWithKakao(): KakaoSignInResponse {
        return signInDataSource.signInWithKakao()
    }
}