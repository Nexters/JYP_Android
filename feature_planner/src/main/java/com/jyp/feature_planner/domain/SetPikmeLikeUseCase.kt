package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import javax.inject.Inject

class SetPikmeLikeUseCase @Inject constructor(
    private val plannerRepository: PlannerRepository,
) {
    suspend operator fun invoke(
        plannerId: String,
        pikmeId: String,
        isLike: Boolean
    ): ApiResult<Any> {
        return apiResult {
            plannerRepository.setPikmeLike(plannerId, pikmeId, isLike)
        }
    }
}
