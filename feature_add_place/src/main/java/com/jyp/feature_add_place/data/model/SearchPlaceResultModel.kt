package com.jyp.feature_add_place.data.model

import com.jyp.feature_add_place.util.JourneyPikiPlaceCategoryEnum


data class SearchPlaceResultModel(
    val name: String,
    val address: String,
    val categoryEnum: JourneyPikiPlaceCategoryEnum,
    val x: Double,
    val y: Double,
    val infoUrl: String
)