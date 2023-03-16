package com.jyp.feature_my_page.di

import com.jyp.feature_my_page.data.WithdrawalRepositoryImpl
import com.jyp.feature_my_page.domain.WithdrawalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class WithdrawalModule {

    @Binds
    abstract fun bindWithdrawalRepository(
        impl: WithdrawalRepositoryImpl
    ): WithdrawalRepository
}
