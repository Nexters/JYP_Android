package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.JypBaseResponse

interface AddPlaceRouteRepository {
    suspend fun setPikis(
        journeyId: String,
        index: Int,
        pikis: List<PlannerPiki>
    ): JypBaseResponse<Any>
}
