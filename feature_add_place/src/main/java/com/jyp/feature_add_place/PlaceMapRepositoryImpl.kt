package com.jyp.feature_add_place

import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.feature_add_place.data.PlaceMapDataSource
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.domain.PlaceMapRepository
import javax.inject.Inject

class PlaceMapRepositoryImpl @Inject constructor(
    private val placeMapDataSource: PlaceMapDataSource,
) : PlaceMapRepository {
    override suspend fun addPikme(
        plannerId: String,
        searchPlaceResultModel: SearchPlaceResultModel
    ): JypBaseResponse<Any> {
        return placeMapDataSource.addPikme(plannerId, searchPlaceResultModel)
    }
}
