package com.jyp.core_network.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JypNetworkInterceptor @Inject constructor(
    private val sessionManager: JypSessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${sessionManager.bearerToken}")

        if (!originalRequest.url.toString().contains("auth/kakao/login")) {
            requestBuilder.addHeader("Content-Type", "application/json")
        }
        return chain.proceed(requestBuilder.build())
    }
}