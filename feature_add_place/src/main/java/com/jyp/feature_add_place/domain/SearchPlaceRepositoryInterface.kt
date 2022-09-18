package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import kotlinx.coroutines.flow.Flow


interface SearchPlaceRepositoryInterface {
    fun getSearchPlaceResult(): Flow<List<SearchPlaceResultModel>>
}