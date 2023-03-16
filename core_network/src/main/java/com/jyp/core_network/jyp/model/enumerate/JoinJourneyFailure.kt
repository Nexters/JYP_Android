package com.jyp.core_network.jyp.model.enumerate

enum class JoinJourneyFailure(
    val code: String,
    val title: String,
    val body: String
) {
    JOURNEY_NOT_EXIST(
        code = "40003",
        title = "아쉽지만 다음에 함께해요!",
        body = "잘못된 참여코드이거나, 이미 삭제된 플래너에요"
    ),
    JOURNEY_JOINED_FULL(
        code = "40004",
        title = "아쉽지만 다음에 함께해요!",
        body = "참여 멤버가 8명을 초과한 경우에는 입장할 수 없어요"
    ),
    ALREADY_JOINED_JOURNEY(
        code = "40005",
        title = "이미 참여 중인 플래너에요!",
        body = "나의 여행에서 확인해 보세요"
    ),
    ALREADY_ENDED_JOURNEY(
        code = "40006",
        title = "아쉽지만 다음에 함께해요!",
        body = "여행 기간이 종료된 플래너에요"
    );

    companion object {
        infix fun getEnumBy(code: String): JoinJourneyFailure? {
            return JoinJourneyFailure.values().firstOrNull {
                it.code == code
            }
        }
    }
}