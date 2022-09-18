package com.jyp.feature_add_place.domain

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun interface GetSearchPlaceUseCase : () -> Flow<Result<List<SearchPlaceResultModel>>>
fun getSearchPlaceResult(
    searchPlaceRepositoryInterface: SearchPlaceRepositoryInterface
): Flow<Result<List<SearchPlaceResultModel>>> {
    return searchPlaceRepositoryInterface
        .getSearchPlaceResult()
        .map {
            resultOf { it }
        }
}

inline fun <R> resultOf(block: () -> R): Result<R> {
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