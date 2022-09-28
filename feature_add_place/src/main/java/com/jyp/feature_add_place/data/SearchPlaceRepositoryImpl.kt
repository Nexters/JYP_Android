package com.jyp.feature_add_place.data

import com.jyp.core.search_place.domain.KakaoLocalRetrofit
import com.jyp.core.search_place.domain.SearchPlaceApi
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.data.mapper.toDataModelList
import com.jyp.feature_add_place.domain.SearchPlaceRepositoryInterface
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchPlaceRepositoryImpl @Inject constructor(
    @KakaoLocalRetrofit private val searchPlaceApi: SearchPlaceApi
) : SearchPlaceRepositoryInterface() {

    override fun getSearchPlaceResult(
        placeName: String
    ): Flow<Result<List<SearchPlaceResultModel>>> {

        return flow {
            delay(300L)
            emit(resultOf {
                searchPlaceApi.getSearchPlaceResult(placeName).documents.map {
                    it.toDataModelList()
                }
            })
        }
    }

    private inline fun <R> resultOf(block: () -> R): Result<R> {
        return try {
            Result.success(block())
        } catch (t: TimeoutCancellationException) {
            Result.failure(t)
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}