package com.jyp.feature_add_place.di

import com.jyp.feature_add_place.data.SearchPlaceRepositoryImpl
import com.jyp.feature_add_place.domain.GetSearchPlaceUseCase
import com.jyp.feature_add_place.domain.SearchPlaceRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [SearchPlaceModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object SearchPlaceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindSearchPlaceRepositoryInterface(
            searchPlaceRepositoryImpl: SearchPlaceRepositoryImpl
        ): SearchPlaceRepositoryInterface
    }
}
