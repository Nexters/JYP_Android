package com.jyp.core_network.jyp.model.request

import com.jyp.core_network.jyp.model.enumerate.TagOrientation


data class JoinPlannerRequestBody(
    val tags: List<Tag>
) {
    data class Tag(
        val topic: String,
        val orientation: TagOrientation,
    )
}
