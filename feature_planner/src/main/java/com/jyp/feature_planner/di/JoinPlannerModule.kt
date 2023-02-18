package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.JoinPlannerRepositoryImpl
import com.jyp.feature_planner.domain.JoinPlannerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class JoinPlannerModule {
    @Binds
    abstract fun bindJoinPlannerRepository(
        impl: JoinPlannerRepositoryImpl
    ): JoinPlannerRepository
}