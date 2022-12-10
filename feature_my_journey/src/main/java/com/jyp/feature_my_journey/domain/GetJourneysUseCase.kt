package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.response.Journeys
import javax.inject.Inject

class GetJourneysUseCase @Inject constructor(
        private val journeyRepository: JourneyRepository,
) {
    suspend operator fun invoke(): ApiResult<Journeys> {
        return apiResult {
            journeyRepository.getJourneys()
        }
    }
}
