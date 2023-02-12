package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.AddPlaceRouteRepositoryImpl
import com.jyp.feature_planner.domain.AddPlaceRouteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddPlannerRouteModule {
    @Binds
    abstract fun bindCreatePlannerRepository(impl: AddPlaceRouteRepositoryImpl): AddPlaceRouteRepository
}
