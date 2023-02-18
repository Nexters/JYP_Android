package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.request.JoinPlannerRequestBody
import javax.inject.Inject


class JoinPlannerDataSource @Inject constructor(
    private val jypApi: JypApi
) {
    suspend fun joinPlanner(
        plannerId: String,
        tags: List<JoinPlannerRequestBody.Tag>
    ): JypBaseResponse<Any> {

        val body = JoinPlannerRequestBody(tags)
        return jypApi.joinPlanner(
            plannerId,
            body
        )
    }
}