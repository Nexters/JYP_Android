package com.jyp.feature_sign_in.questions.di

import com.jyp.feature_sign_in.questions.data.QuestionRepository
import com.jyp.feature_sign_in.questions.data.QuestionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class QuestionModule {
    @Binds
    abstract fun bindQuestionRepository(impl: QuestionRepositoryImpl): QuestionRepository
}
