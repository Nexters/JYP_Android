package com.jyp.core_network.jyp.model

data class Journey(
        val id: String,
        val name: String,
        val themePath: String,
        val startDate: Long,
        val endDate: Long,
        val users: List<User>,
)
