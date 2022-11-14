package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.response.JourneyUser
import javax.inject.Inject

class GetJourneysUseCase @Inject constructor(
        private val journeyRepository: JourneyRepository,
) {
    suspend operator fun invoke(): ApiResult<JourneyUser> {
        return apiResult {
            journeyRepository.getJourneys()
        }
    }
}
