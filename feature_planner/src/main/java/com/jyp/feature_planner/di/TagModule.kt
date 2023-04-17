package com.jyp.feature_planner.di

import com.jyp.feature_planner.data.TagRepositoryImpl
import com.jyp.feature_planner.domain.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TagModule {
    @Binds
    abstract fun bindPlannerRepository(impl: TagRepositoryImpl): TagRepository
}