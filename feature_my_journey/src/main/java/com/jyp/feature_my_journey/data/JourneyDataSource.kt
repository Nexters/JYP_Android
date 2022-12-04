package com.jyp.feature_my_journey.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.JourneysResponse
import javax.inject.Inject

class JourneyDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun getJourneys(): JourneysResponse {
        return jypApi.getJourneys()
    }
}
