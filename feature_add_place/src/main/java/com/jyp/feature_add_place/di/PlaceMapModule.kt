package com.jyp.feature_add_place.di

import com.jyp.feature_add_place.PlaceMapRepositoryImpl
import com.jyp.feature_add_place.domain.PlaceMapRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PlaceMapModule {
    @Binds
    fun bindPlaceMapRepository(impl: PlaceMapRepositoryImpl): PlaceMapRepository
}
