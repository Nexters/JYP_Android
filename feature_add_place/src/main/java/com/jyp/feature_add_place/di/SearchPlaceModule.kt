package com.jyp.feature_add_place.di

import android.util.Log
import com.jyp.feature_add_place.data.SearchPlaceRepositoryImpl
import com.jyp.feature_add_place.domain.GetSearchPlaceUseCase
import com.jyp.feature_add_place.domain.SearchPlaceRepositoryInterface
import com.jyp.feature_add_place.domain.getSearchPlaceResult
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [SearchPlaceModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object SearchPlaceModule {

    @Provides
    fun provideGetSearchPlaceUseCase(
        searchPlaceRepositoryInterface: SearchPlaceRepositoryInterface,
        @Named("placeName") placeName: String
    ): GetSearchPlaceUseCase {
        Log.d("TAG", "module - provideGetSearchPlaceUseCase: $placeName")
        return GetSearchPlaceUseCase {
            getSearchPlaceResult(searchPlaceRepositoryInterface, placeName)
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
    @Provides
    @Named("placeName")
    fun providePlaceName(): String = ""
}
