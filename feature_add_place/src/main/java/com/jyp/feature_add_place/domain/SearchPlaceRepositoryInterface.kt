package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Named


abstract class SearchPlaceRepositoryInterface {
    abstract fun getSearchPlaceResult(
        placeName: String
    ): Flow<List<SearchPlaceResultModel>>
}