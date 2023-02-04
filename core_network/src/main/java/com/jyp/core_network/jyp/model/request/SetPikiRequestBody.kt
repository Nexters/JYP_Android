package com.jyp.core_network.jyp.model.request

import com.jyp.core_network.jyp.model.Piki

data class SetPikiRequestBody(
    val index: Int,
    val pikis: List<Piki>,
)
