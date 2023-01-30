package com.jyp.core_network.jyp.model

import com.jyp.core_network.jyp.model.enumerate.PlaceCategory

data class Pikmis(
    val pikmis: List<Pikme>
)

data class Pikme(
    val id: String,
    val name: String,
    val address: String,
    val category: PlaceCategory,
    val likeBy: List<User>,
    val longitude: Double,
    val latitude: Double,
    val link: String,
)
