package com.jyp.feature_my_journey.presentation.my_journey

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jyp.feature_my_journey.R
import com.jyp.jyp_design.resource.JypColors

enum class ThemeType(
        val backgroundColor: Color,
        val profileBorderColor: Color,
        @DrawableRes val imageRes: Int,
) {
    OCEAN(
            backgroundColor = Color(0xFF88C4FF),
            profileBorderColor = Color(0xFF57ABFF),
            imageRes = R.drawable.bg_journey_theme_ocean,
    ),
    MOUNTAIN(
            backgroundColor = Color(0xFFF15077),
            profileBorderColor = Color(0xFFEF597D),
            imageRes = R.drawable.bg_journey_theme_mountain,
    ),
    CULTURE(
            backgroundColor = Color(0xFFFFA451),
            profileBorderColor = Color(0xFFFFA95B),
            imageRes = R.drawable.bg_journey_theme_culture,
    ),
    CITY(
            backgroundColor = JypColors.Sub_blue200,
            profileBorderColor = Color(0xFF58C6E9),
            imageRes = R.drawable.bg_journey_theme_city,
    ),

}
