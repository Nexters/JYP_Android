package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import javax.inject.Inject

class SetPikisUseCase @Inject constructor(
    private val addPlaceRouteRepository: AddPlaceRouteRepository,
) {
    suspend operator fun invoke(
        journeyId: String,
        index: Int,
        pikis: List<PlannerPiki>
    ): ApiResult<Any> {
        return apiResult {
            addPlaceRouteRepository.setPikis(journeyId, index, pikis)
        }
    }
}
