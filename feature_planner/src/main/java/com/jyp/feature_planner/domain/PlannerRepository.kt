package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.JypWithoutDataResponse
import com.jyp.core_network.jyp.model.response.PlannerResponse

interface PlannerRepository {
    suspend fun getPlanner(id: String): PlannerResponse

    suspend fun setPikmeLike(
        plannerId: String,
        pikmeId: String,
        isLike: Boolean,
    ): JypWithoutDataResponse
}
