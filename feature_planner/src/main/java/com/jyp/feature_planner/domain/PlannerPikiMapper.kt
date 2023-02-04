package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Piki
import com.jyp.core_network.jyp.model.enumerate.toName

class PlannerPikiMapper {
    fun toPlannerPiki(piki: Piki): PlannerPiki {
        return PlannerPiki(
            id = piki.id,
            name = piki.name,
            address = piki.address,
            category = piki.category.toName(),
            longitude = piki.longitude,
            latitude = piki.latitude,
            link = piki.link,
        )
    }
}
