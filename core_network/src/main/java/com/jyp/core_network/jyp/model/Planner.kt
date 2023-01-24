package com.jyp.core_network.jyp.model

data class Planner(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val themePath: String,
    val users: List<User>,
    val tags: List<Tag>,
    val pikidays: List<Pikis>,
)
