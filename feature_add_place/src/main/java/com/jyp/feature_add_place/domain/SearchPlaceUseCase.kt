package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSearchPlaceUseCase @Inject constructor(
    private val searchPlaceRepositoryInterface: SearchPlaceRepositoryInterface
) {
    operator fun invoke(
        placeName: String
    ): Flow<Result<List<SearchPlaceResultModel>>> {
        return searchPlaceRepositoryInterface.getSearchPlaceResult(placeName)
    }
}