package com.jyp.feature_my_journey.enum

enum class FailToJoinJourneyEnum(
    val title: String,
    val body: String
) {
    FAIL_WITH_EXPIRED(
        title = "이미 비행기가 떠났어요!",
        body = "아쉽지만 다음에 함께해요"
    ),
    FAIL_WITH_EXCEEDED(
        title = "아쉽지만 다음에 함께해요!",
        body = "최대 8명까지 입장할 수 있어요"
    )
}