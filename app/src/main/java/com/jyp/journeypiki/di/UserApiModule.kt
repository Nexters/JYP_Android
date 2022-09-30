package com.jyp.journeypiki.di

import com.jyp.journeypiki.data.UserInfoRepositoryImpl
import com.jyp.journeypiki.domain.UserInfoRepositoryInterface
import com.jyp.journeypiki.domain.UserInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [UserApiModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object UserApiModule {

    @Provides
    fun provideGetUserInfoUseCase(
        userInfoRepositoryInterface: UserInfoRepositoryInterface
    ): UserInfoUseCase {
        return UserInfoUseCase(userInfoRepositoryInterface)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindUserInfoRepositoryInterface(
            userInfoRepositoryImpl: UserInfoRepositoryImpl
        ): UserInfoRepositoryInterface
    }
}