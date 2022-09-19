package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.SearchPlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [SearchPlaceModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object SearchPlaceModule {

    @Provides
    fun provideGetSearchPlaceUseCase(
        searchPlaceRepositoryInterface: SearchPlaceRepositoryInterface
    ): GetSearchPlaceUseCase {
        return GetSearchPlaceUseCase {
            getSearchPlaceResult(searchPlaceRepositoryInterface)
        }
    }

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
