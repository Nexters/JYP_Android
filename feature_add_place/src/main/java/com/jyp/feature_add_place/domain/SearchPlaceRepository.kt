package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import kotlinx.coroutines.flow.Flow


interface SearchPlaceRepository {
    abstract fun getSearchPlaceResult(
        placeName: String
    ): Flow<Result<List<SearchPlaceResultModel>>>
}