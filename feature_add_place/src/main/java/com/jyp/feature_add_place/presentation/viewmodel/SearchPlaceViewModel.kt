package com.jyp.feature_add_place.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.feature_add_place.presentation.ApiState
import com.jyp.feature_add_place.domain.GetSearchPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val getSearchPlaceUseCase: GetSearchPlaceUseCase
) : ViewModel() {

    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState = _apiState.asStateFlow()

    private var searchPlaceJob: Job? = null


    fun getSearchPlaceResult(placeName: String) {
        searchPlaceJob?.cancel()
        searchPlaceJob = viewModelScope.launch(Dispatchers.IO) {
            getSearchPlaceUseCase(placeName)
                .onStart { _apiState.emit(ApiState.Loading) }
                .cancellable()
                .collect { result ->
                    result
                        .onSuccess { searchPlaceResult ->
                            _apiState.emit(ApiState.Success(searchPlaceResult))
                        }
                        .onFailure { throwable ->
                            _apiState.emit(ApiState.Failure(throwable.stackTrace.toString()))
                        }
                }
        }
    }
}

