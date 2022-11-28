package com.jyp.jyp_design.enumerate

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors

enum class ThemeType(
        val textColor: Color,
        val backgroundColor: Color,
        val profileBorderColor: Color,
        @DrawableRes val imageRes: Int,
        val description: String,
        val imagePath: String,
) {
    DEFAULT(
            textColor = JypColors.Text80,
            backgroundColor = JypColors.Background_white100,
            profileBorderColor = JypColors.Background_grey300,
            imageRes = R.drawable.bg_journey_theme_default,
            description = "기본",
            imagePath = "https://journeypiki.duckdns.org/static/theme_basic.png",
    ),
    OCEAN(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFF88C4FF),
            profileBorderColor = Color(0xFF57ABFF),
            imageRes = R.drawable.bg_journey_theme_ocean,
            description = "바다",
            imagePath = "https://journeypiki.duckdns.org/static/theme_beach.png",
    ),
    MOUNTAIN(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFFF15077),
            profileBorderColor = Color(0xFFEF597D),
            imageRes = R.drawable.bg_journey_theme_mountain,
            description = "산",
            imagePath = "https://journeypiki.duckdns.org/static/theme_mountain.png",
    ),
    CULTURE(
            textColor = JypColors.Text_white,
            backgroundColor = Color(0xFFFFA451),
            profileBorderColor = Color(0xFFFFA95B),
            imageRes = R.drawable.bg_journey_theme_culture,
            description = "문화 유적",
            imagePath = "https://journeypiki.duckdns.org/static/theme_historic_spot.png",
    ),
    CITY(
            textColor = JypColors.Text_white,
            backgroundColor = JypColors.Sub_blue200,
            profileBorderColor = Color(0xFF58C6E9),
            imageRes = R.drawable.bg_journey_theme_city,
            description = "도시",
            imagePath = "https://journeypiki.duckdns.org/static/theme_city.png",
    ),
}
