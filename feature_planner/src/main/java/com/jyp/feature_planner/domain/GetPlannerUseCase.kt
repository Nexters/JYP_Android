package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.Planner
import javax.inject.Inject

class GetPlannerUseCase @Inject constructor(
    private val plannerRepository: PlannerRepository,
) {
    suspend operator fun invoke(id: String): ApiResult<Planner> {
        return apiResult {
            plannerRepository.getPlanner(id)
        }
    }
}
