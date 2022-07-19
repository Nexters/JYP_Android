package com.jyp.jypandroid.di

import com.jyp.core.kakao_search.KakaoSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KakaoSearchModule {
    @Provides
    @Singleton
    fun provideKakaoSearchApi(): KakaoSearchApi {
        return getRetrofit().create()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    chain.request().newBuilder()
                            .addHeader("Authorization", "KakaoAK $REST_API_KEY")
                            .build()
                            .let(chain::proceed)
                }
                .build()
    }

    companion object {
        private const val REST_API_KEY ="2e6ac72aaf904e0edcf6e4a8e537dfa5"
    }
}
