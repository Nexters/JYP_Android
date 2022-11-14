package com.jyp.feature_planner.domain

interface CreatePlannerRepository {
    suspend fun createPlanner(
            title: String,
            startSec: Long,
            endSec: Long,
            themePath: String,
            tags: List<Tag>
    ): Any
}
