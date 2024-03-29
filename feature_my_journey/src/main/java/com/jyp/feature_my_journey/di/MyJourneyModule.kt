package com.jyp.feature_my_journey.di

import com.jyp.feature_my_journey.data.JourneyRepositoryImpl
import com.jyp.feature_my_journey.domain.JourneyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyJourneyModule {
    @Binds
    abstract fun bindJourneyRepository(impl: JourneyRepositoryImpl): JourneyRepository
}
