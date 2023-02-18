package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.Pikis
import com.jyp.core_network.jyp.model.request.SetPikiRequestBody
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.domain.PlannerPikiMapper
import javax.inject.Inject

class AddPlannerRouteDataSource @Inject constructor(
    private val jypApi: JypApi,
) {
    suspend fun setPikis(
        journeyId: String,
        index: Int,
        pikis: List<PlannerPiki>
    ): JypBaseResponse<Any> {
        val plannerPikiMapper = PlannerPikiMapper()

        return jypApi.setPikis(
            journeyId,
            SetPikiRequestBody(
                index = index,
                pikis = pikis.map(plannerPikiMapper::toPiki),
            ),
        )
    }
}
