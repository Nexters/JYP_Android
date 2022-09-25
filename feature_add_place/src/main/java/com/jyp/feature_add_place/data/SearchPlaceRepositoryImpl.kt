package com.jyp.feature_add_place.data

import android.util.Log
import com.jyp.core.search_place.domain.SearchPlaceApi
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.data.mapper.toDataModelList
import com.jyp.feature_add_place.domain.SearchPlaceRepositoryInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchPlaceRepositoryImpl @Inject constructor(
    private val searchPlaceApi: SearchPlaceApi
): SearchPlaceRepositoryInterface() {

    override fun getSearchPlaceResult(
        placeName: String
    ): Flow<List<SearchPlaceResultModel>> {

        return flow {
            delay(2000L)
            searchPlaceApi.getSearchPlaceResult(placeName).documents.map {
                it.toDataModelList()
            }
        }
    }
}