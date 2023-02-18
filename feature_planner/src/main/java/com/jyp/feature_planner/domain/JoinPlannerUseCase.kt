package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.response.Journeys
import javax.inject.Inject


class JoinPlannerUseCase @Inject constructor(
    private val joinPlannerRepository: JoinPlannerRepository,
) {
    suspend operator fun invoke(
        plannerId: String,
        tags: List<PlannerTag>
    ): ApiResult<Any> {

        val tagMapper = PlannerTagMapper()
        return apiResult {
            joinPlannerRepository.joinPlanner(
                plannerId,
                tags.map(tagMapper::toJoinTag)
            )
        }
    }
}