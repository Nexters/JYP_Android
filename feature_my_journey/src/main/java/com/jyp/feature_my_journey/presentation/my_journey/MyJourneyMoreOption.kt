package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.ui.graphics.painter.Painter

data class MyJourneyMoreOption(
        val icon: Painter,
        val title: String,
        val onClickOption: () -> Unit,
)
