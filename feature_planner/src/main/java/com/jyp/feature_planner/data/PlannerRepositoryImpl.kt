package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.model.response.PlannerResponse
import com.jyp.feature_planner.domain.PlannerRepository
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val plannerDataSource: PlannerDataSource,
) : PlannerRepository {
    override suspend fun getPlanner(id: String): PlannerResponse {
        return plannerDataSource.getPlanner(id)
    }
}
