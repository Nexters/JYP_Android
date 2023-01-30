package com.jyp.feature_add_place.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.apiResult
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import javax.inject.Inject

class AddPikmeUseCase @Inject constructor(
    private val placeMapRepository: PlaceMapRepository,
) {
    suspend operator fun invoke(
        plannerId: String,
        searchPlaceResultModel: SearchPlaceResultModel
    ): ApiResult<Any> {
        return apiResult {
            placeMapRepository.addPikme(plannerId, searchPlaceResultModel)
        }
    }

}
