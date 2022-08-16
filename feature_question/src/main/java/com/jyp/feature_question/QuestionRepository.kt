package com.jyp.feature_question

interface QuestionRepository {
    fun getQuestion(): List<QuestionData>
}