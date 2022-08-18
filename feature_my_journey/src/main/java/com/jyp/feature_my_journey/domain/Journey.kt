package com.jyp.feature_my_journey.domain

data class Journey(
        val dDay: String,
        val title: String,
        val theme: Int,
        val startDay: String,
        val endDay: String,
        val profileUrls: List<String>,
)
