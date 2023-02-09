package com.jyp.journeypiki.di

import com.jyp.journeypiki.data.SplashRepositoryImpl
import com.jyp.journeypiki.domain.SplashRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashModule {
    @Binds
    abstract fun bindSplashRepository(impl: SplashRepositoryImpl): SplashRepository
}
