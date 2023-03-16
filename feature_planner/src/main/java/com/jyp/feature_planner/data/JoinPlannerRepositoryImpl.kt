package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.request.JoinPlannerRequestBody
import com.jyp.feature_planner.domain.JoinPlannerRepository
import javax.inject.Inject


class JoinPlannerRepositoryImpl @Inject constructor(
    private val joinPlannerDataSource: JoinPlannerDataSource
) : JoinPlannerRepository {

    override suspend fun joinPlanner(
        plannerId: String,
        tags: List<JoinPlannerRequestBody.Tag>
    ): JypBaseResponse<Any> {
        return joinPlannerDataSource.joinPlanner(
            plannerId,
            tags
        )
    }
}