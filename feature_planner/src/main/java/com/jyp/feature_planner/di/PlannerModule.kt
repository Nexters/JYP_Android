package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.PlannerRepositoryImpl
import com.jyp.feature_planner.domain.PlannerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlannerModule {
    @Binds
    abstract fun bindPlannerRepository(impl: PlannerRepositoryImpl): PlannerRepository
}
