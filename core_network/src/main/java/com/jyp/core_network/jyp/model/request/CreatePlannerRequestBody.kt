package com.jyp.core_network.jyp.model.request

import com.jyp.core_network.jyp.model.Tag

data class CreatePlannerRequestBody(
        val name: String,
        val startDate: Long,
        val endDate: Long,
        val themePath: String,
        val tags: List<Tag>,
)
