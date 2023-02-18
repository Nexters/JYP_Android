package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.feature_planner.domain.AddPlaceRouteRepository
import com.jyp.feature_planner.domain.PlannerPiki
import javax.inject.Inject

class AddPlaceRouteRepositoryImpl @Inject constructor(
    private val addPlaceRouteDataSource: AddPlannerRouteDataSource,
) : AddPlaceRouteRepository {
    override suspend fun setPikis(
        journeyId: String,
        index: Int,
        pikis: List<PlannerPiki>
    ): JypBaseResponse<Any> {
        return addPlaceRouteDataSource.setPikis(journeyId, index, pikis)
    }
}
