package com.jyp.feature_question.util

import com.jyp.feature_question.R


enum class QuestionResultEnum(
    val nameRes: Int,
    val code: String,
    val serialNumbers: List<Int>
) {
    ME(
        nameRes = R.string.question_result_me,
        code = "ME",
        serialNumbers = listOf(111, 211)
    ),
    PE(
        nameRes = R.string.question_result_pe,
        code = "PE",
        serialNumbers = listOf(121, 112)
    ),
    RT(
        nameRes = R.string.question_result_rt,
        code = "RT",
        serialNumbers = listOf(221, 212)
    ),
    FW(
        nameRes = R.string.question_result_fw,
        code = "FW",
        serialNumbers = listOf(122, 222)
    )
}