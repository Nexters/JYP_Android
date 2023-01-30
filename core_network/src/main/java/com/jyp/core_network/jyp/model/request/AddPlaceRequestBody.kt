package com.jyp.core_network.jyp.model.request

import com.jyp.core_network.jyp.model.enumerate.PlaceCategory

data class AddPlaceRequestBody(
    val name: String,
    val address: String,
    val category: PlaceCategory,
    val longitude: Double,
    val latitude: Double,
    val link: String,
)
