package com.jyp.core_network.jyp.model.response

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.Journey
import com.jyp.core_network.jyp.model.User

class JourneysResponse : JypBaseResponse<JourneyUser>()

data class JourneyUser(
        val journeys: List<Journey>,
        val users: List<User>,
)
