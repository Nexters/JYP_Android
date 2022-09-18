package com.jyp.feature_add_place.data.model

data class SearchPlaceResultModel(
    val name: String,
    val address: String,
    val category: String,
    val x: Double,
    val y: Double,
    val infoUrl: String
)