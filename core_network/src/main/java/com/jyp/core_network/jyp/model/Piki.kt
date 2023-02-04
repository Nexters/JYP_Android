package com.jyp.core_network.jyp.model

import com.jyp.core_network.jyp.model.enumerate.PlaceCategory

data class Pikis(
    val pikis: List<Piki>,
)

data class Piki(
    val id: String,
    val name: String,
    val address: String,
    val category: PlaceCategory,
    val longitude: Long,
    val latitude: Long,
    val link: String,
)
