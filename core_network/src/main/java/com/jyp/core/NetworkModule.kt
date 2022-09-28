package com.jyp.core

import com.jyp.core.search_place.domain.KakaoLocalRetrofit
import com.jyp.core.search_place.domain.KakaoLocalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @KakaoLocalRetrofit
    fun provideKakaoLocalApi(
        @KakaoLocalRetrofit retrofit: Retrofit
    ): KakaoLocalApi {
        return retrofit.create(KakaoLocalApi::class.java)
    }

    @Provides
    @Singleton
    @KakaoLocalRetrofit
    fun provideKakaoLocalRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dapi.kakao.com")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .build()
                    .let(chain::proceed)
            }
            .build()
    }
}
