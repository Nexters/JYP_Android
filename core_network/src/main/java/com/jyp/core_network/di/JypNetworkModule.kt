package com.jyp.core_network.di

import android.content.Context
import android.content.SharedPreferences
import com.jyp.core_network.jyp.JypApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideJypApi(
        jypNetworkInterceptor: JypNetworkInterceptor
    ): JypApi {
        return getRetrofit(jypNetworkInterceptor).create()
    }

    @Provides
    @Singleton
    @JourneyPikiRetrofit
    fun getRetrofit(
        jypNetworkInterceptor: JypNetworkInterceptor
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://journeypiki.duckdns.org/")
                .client(getOkHttpClient(jypNetworkInterceptor))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    @JourneyPikiRetrofit
    fun getOkHttpClient(
        jypNetworkInterceptor: JypNetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    jypNetworkInterceptor.intercept(chain)
                }
                .build()
    }

    @Provides
    @Singleton
    @JourneyPikiRetrofit
    fun provideOkhttpInterceptor(
        jypNetworkInterceptor: JypNetworkInterceptor
    ): JypNetworkInterceptor {
        return jypNetworkInterceptor
    }

    @Provides
    @Singleton
    fun provideTokenSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "TOKEN", Context.MODE_PRIVATE
        )
    }
}
