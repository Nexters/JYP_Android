package com.jyp.feature_sign_in.questions

import com.jyp.feature_sign_in.R


enum class QuestionResultEnum(
    val nameRes: Int,
    val serialNumbers: List<String>
) {
    ME(
        nameRes = R.string.question_result_me,
        serialNumbers = listOf("000", "100")
    ),
    PE(
        nameRes = R.string.question_result_pe,
        serialNumbers = listOf("010", "001")
    ),
    RT(
        nameRes = R.string.question_result_rt,
        serialNumbers = listOf("110", "101")
    ),
    FW(
        nameRes = R.string.question_result_fw,
        serialNumbers = listOf("011", "111")
    )
}