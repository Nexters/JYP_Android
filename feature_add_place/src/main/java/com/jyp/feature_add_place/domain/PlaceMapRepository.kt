package com.jyp.feature_add_place.domain

import com.jyp.core_network.jyp.model.response.AddPikmeResponse
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel

interface PlaceMapRepository {
    suspend fun addPikme(
        plannerId: String,
        searchPlaceResultModel: SearchPlaceResultModel,
    ): AddPikmeResponse
}
