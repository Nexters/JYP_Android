package com.jyp.feature_my_journey.presentation.my_journey

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jyp.feature_my_journey.R
import com.jyp.jyp_design.resource.JypColors

enum class ThemeType(
        val textColor: Color,
        val backgroundColor: Color,
        val profileBorderColor: Color,
        @DrawableRes val imageRes: Int,
) {
    DEFAULT(
            textColor = JypColors.Text80,
            backgroundColor = JypColors.Background_white100,
            profileBorderColor = JypColors.Background_grey300,
            imageRes = R.drawable.bg_journey_theme_default,
    ),
    OCEAN(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFF88C4FF),
            profileBorderColor = Color(0xFF57ABFF),
            imageRes = R.drawable.bg_journey_theme_ocean,
    ),
    MOUNTAIN(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFFF15077),
            profileBorderColor = Color(0xFFEF597D),
            imageRes = R.drawable.bg_journey_theme_mountain,
    ),
    CULTURE(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFFFFA451),
            profileBorderColor = Color(0xFFFFA95B),
            imageRes = R.drawable.bg_journey_theme_culture,
    ),
    CITY(
            textColor = JypColors.Text_white,
            backgroundColor = JypColors.Sub_blue200,
            profileBorderColor = Color(0xFF58C6E9),
            imageRes = R.drawable.bg_journey_theme_city,
    ),
}
