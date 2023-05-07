package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiUnitResult
import com.jyp.core_network.jyp.model.request.JoinPlannerRequestBody
import javax.inject.Inject

class EditTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(
        plannerId: String,
        tags: List<PlannerTag>
    ): ApiResult<Any> {

        val tagMapper = PlannerTagMapper()
        return apiUnitResult {
            tagRepository.editTags(
                plannerId,
                JoinPlannerRequestBody(
                    tags.map(tagMapper::toJoinTag)
                )
            )
        }
    }
}