package com.jyp.feature_question

enum class QuestionEnum(
    val index: Int,
    val titleRes: Int,
    val optionsRes: Int,
    val images: List<Int>
) {
    QUESTION_01(
        index = 0,
        titleRes = R.string.question01,
        optionsRes = R.array.question01_options,
        images = listOf(
            R.drawable.illust_question01_01,
            R.drawable.illust_question01_02
        )
    ),
    QUESTION_02(
        index = 1,
        titleRes = R.string.question02,
        optionsRes = R.array.question02_options,
        images = listOf(
            R.drawable.illust_question02_01,
            R.drawable.illust_question02_02
        )
    ),
    QUESTION_03(
        index = 2,
        titleRes = R.string.question03,
        optionsRes = R.array.question03_options,
        images = listOf(
            R.drawable.illust_question03_01,
            R.drawable.illust_question03_02
        )
    )
}