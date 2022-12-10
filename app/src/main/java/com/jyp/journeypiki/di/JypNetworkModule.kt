package com.jyp.journeypiki.di

import com.jyp.core_network.jyp.JypApi
import com.jyp.journeypiki.BuildConfig
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
class JypNetworkModule {
    @Provides
    @Singleton
    fun provideJypApi(): JypApi {
        return getRetrofit().create()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://journeypiki.duckdns.org/")
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
                            .addHeader("jyp-jwt-master-key", BuildConfig.JYP_JWT_MASTER_KEY)
                            .addHeader("jyp-override-id", BuildConfig.JYP_OVERRIDE_ID)
                            .build()
                            .let(chain::proceed)
                }
                .build()
    }
}
