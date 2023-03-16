package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.request.JoinPlannerRequestBody


interface JoinPlannerRepository {
    suspend fun joinPlanner(
        plannerId: String,
        tags: List<JoinPlannerRequestBody.Tag>
    ): JypBaseResponse<Any>
}