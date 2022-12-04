package com.jyp.feature_my_journey.domain

import com.jyp.jyp_design.enumerate.ThemeType

data class Journey(
        val dDay: String,
        val title: String,
        val themeType: ThemeType,
        val startDay: String,
        val endDay: String,
        val profileUrls: List<String>,
)
