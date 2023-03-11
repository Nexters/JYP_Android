package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.response.PlannerResponse

interface PlannerRepository {
    suspend fun getPlanner(id: String): PlannerResponse

    suspend fun setPikmeLike(
        plannerId: String,
        pikmeId: String,
        isLike: Boolean,
    ): JypBaseResponse<Any>
}
