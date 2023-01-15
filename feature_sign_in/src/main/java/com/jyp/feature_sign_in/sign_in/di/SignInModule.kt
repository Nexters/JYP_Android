package com.jyp.feature_sign_in.sign_in.di

import com.jyp.feature_sign_in.sign_in.data.SignInRepository
import com.jyp.feature_sign_in.sign_in.data.SignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class SignInModule {
    @Binds
    abstract fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository
}
