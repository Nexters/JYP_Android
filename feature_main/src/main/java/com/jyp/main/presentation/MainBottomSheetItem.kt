package com.jyp.main.presentation

import com.jyp.feature_my_journey.domain.Journey

sealed class MainBottomSheetItem {
    object None : MainBottomSheetItem()
    data class JourneyMore(val journey: Journey) : MainBottomSheetItem()
    data class ConfirmRemoveJourney(val journey: Journey) : MainBottomSheetItem()
}