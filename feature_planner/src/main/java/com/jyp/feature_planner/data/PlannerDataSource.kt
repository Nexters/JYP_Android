package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.response.PlannerResponse
import javax.inject.Inject

class PlannerDataSource @Inject constructor(
    private val jypApi: JypApi,
) {
    suspend fun getPlanner(id: String): PlannerResponse {
        return jypApi.getPlanner(id)
    }

    suspend fun likePikme(plannerId: String, pikmeId: String): JypBaseResponse<Any> {
        return jypApi.likePikme(plannerId, pikmeId)
    }

    suspend fun undoLikePikme(plannerId: String, pikmeId: String): JypBaseResponse<Any> {
        return jypApi.undoLikePikme(plannerId, pikmeId)
    }
}
