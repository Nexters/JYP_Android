package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.CreatePlannerRepositoryImpl
import com.jyp.feature_planner.domain.CreatePlannerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CreatePlannerModule {
    @Binds
    abstract fun bindCreatePlannerRepository(impl: CreatePlannerRepositoryImpl): CreatePlannerRepository
}
