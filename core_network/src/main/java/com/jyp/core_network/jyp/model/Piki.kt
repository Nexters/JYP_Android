package com.jyp.core_network.jyp.model

data class Pikis(
    val pikis: List<Piki>,
)

data class Piki(
    val id: String,
    val name: String,
    val address: String,
    val category: Any,
    val longitude: Long,
    val latitude: Long,
    val link: String,
)
