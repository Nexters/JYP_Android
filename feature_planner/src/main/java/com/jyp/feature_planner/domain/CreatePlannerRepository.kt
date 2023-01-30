package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Tag

interface CreatePlannerRepository {
    suspend fun createPlanner(
            title: String,
            startSec: Long,
            endSec: Long,
            themePath: String,
            tags: List<Tag>
    ): Any
}
