package com.jyp.feature_my_journey.data

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.JypWithoutDataResponse
import com.jyp.core_network.jyp.model.response.JourneysResponse
import com.jyp.feature_my_journey.domain.JourneyRepository
import javax.inject.Inject

class JourneyRepositoryImpl @Inject constructor(
        private val journeyDataSource: JourneyDataSource,
) : JourneyRepository {
    override suspend fun getJourneys(): JourneysResponse {
        return journeyDataSource.getJourneys()
    }

    override suspend fun leaveJourney(
        journeyId: String
    ): JypWithoutDataResponse {
        return journeyDataSource.leaveJourney(journeyId)
    }
}
