package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.PlannerResponse
import javax.inject.Inject

class PlannerDataSource @Inject constructor(
    private val jypApi: JypApi,
) {
    suspend fun getPlanner(id: String): PlannerResponse {
        return jypApi.getPlanner(id)
    }
}
