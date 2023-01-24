package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Piki

class PlannerPikiMapper {
    fun toPlannerPiki(piki: Piki): PlannerPiki {
        return PlannerPiki(
            placeName = piki.name,
            address = piki.address,
        )
    }
}
