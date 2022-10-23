package com.jyp.feature_add_place.di

import com.jyp.feature_add_place.data.SearchPlaceRepositoryImpl
import com.jyp.feature_add_place.domain.SearchPlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface SearchPlaceModule {
    @Binds
    fun bindSearchPlaceRepository(
        searchPlaceRepositoryImpl: SearchPlaceRepositoryImpl
    ): SearchPlaceRepository
}
