package com.jyp.feature_add_place.data

import com.jyp.core.search_place.domain.SearchPlaceApi
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.data.mapper.toDataModelList
import com.jyp.feature_add_place.domain.SearchPlaceRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchPlaceRepositoryImpl @Inject constructor(
    private val searchPlaceApi: SearchPlaceApi,
    private val searchPlaceKeyword: String
): SearchPlaceRepositoryInterface {

    override fun getSearchPlaceResult(): Flow<List<SearchPlaceResultModel>> {
        return flow {
            searchPlaceApi.getSearchPlaceResult(searchPlaceKeyword).documents.map {
                it.toDataModelList()
            }
        }
    }
}