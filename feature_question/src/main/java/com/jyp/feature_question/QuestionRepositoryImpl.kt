package com.jyp.feature_question


object QuestionRepositoryImpl: QuestionRepository {
    override fun getQuestion(): List<QuestionData> {
        return listOf(
            QuestionData(
                id = 0,
                titleRes = R.string.question01,
                optionsRes = R.array.question01_options,
                images = listOf(
                    R.drawable.illust_question01_01,
                    R.drawable.illust_question01_02
                ),
                null
            ),
            QuestionData(
                id = 1,
                titleRes = R.string.question02,
                optionsRes = R.array.question02_options,
                images = listOf(
                    R.drawable.illust_question02_01,
                    R.drawable.illust_question02_02
                ),
                null
            ),
            QuestionData(
                id = 2,
                titleRes = R.string.question03,
                optionsRes = R.array.question03_options,
                images = listOf(
                    R.drawable.illust_question03_01,
                    R.drawable.illust_question03_02
                ),
                null
            )
        )
    }

}