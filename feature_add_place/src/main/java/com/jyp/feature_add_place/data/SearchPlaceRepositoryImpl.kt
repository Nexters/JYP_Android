package com.jyp.feature_add_place.data

import com.jyp.core_network.di.KakaoLocalRetrofit
import com.jyp.core_network.kakao.KakaoLocalApi
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.data.mapper.toSearchPlaceResultModel
import com.jyp.feature_add_place.domain.SearchPlaceRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchPlaceRepositoryImpl @Inject constructor(
    @KakaoLocalRetrofit private val kakaoLocalApi: KakaoLocalApi
) : SearchPlaceRepository {

    override fun getSearchPlaceResult(
        placeName: String
    ): Flow<Result<List<SearchPlaceResultModel>>> {

        return flow {
            delay(300L)
            emit(resultOf {
                kakaoLocalApi.getKakaoLocalSearchResult(placeName).documents.map {
                    it.toSearchPlaceResultModel()
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