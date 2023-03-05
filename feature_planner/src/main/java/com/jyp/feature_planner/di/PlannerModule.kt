package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.PlannerRepositoryImpl
import com.jyp.feature_planner.data.UserRepositoryImpl
import com.jyp.feature_planner.domain.PlannerRepository
import com.jyp.feature_planner.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlannerModule {
    @Binds
    abstract fun bindPlannerRepository(impl: PlannerRepositoryImpl): PlannerRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
