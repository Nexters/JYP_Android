package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.model.response.JourneysResponse

interface JourneyRepository {
    suspend fun getJourneys(): JourneysResponse
}
