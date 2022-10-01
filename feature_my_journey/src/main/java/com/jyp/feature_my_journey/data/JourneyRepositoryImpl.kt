package com.jyp.feature_my_journey.data

import com.jyp.feature_my_journey.domain.JourneyRepository
import javax.inject.Inject

class JourneyRepositoryImpl @Inject constructor(
        private val journeyDataSource: JourneyDataSource,
) : JourneyRepository {
    override suspend fun getJourneys(): Any {
        return journeyDataSource.getJourneys()
    }
}
