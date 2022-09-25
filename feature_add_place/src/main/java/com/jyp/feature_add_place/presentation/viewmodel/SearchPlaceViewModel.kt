package com.jyp.feature_add_place.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.feature_add_place.presentation.UiState
import com.jyp.feature_add_place.domain.GetSearchPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val getSearchPlaceUseCase: GetSearchPlaceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var searchPlaceJob: Job? = null


    fun getSearchPlaceResult(placeName: String) {
        Log.d("TAG", "getSearchPlaceResult: $placeName")

        searchPlaceJob?.cancel()
        searchPlaceJob = viewModelScope.launch(Dispatchers.IO) {
            getSearchPlaceUseCase.getSearchPlaceResult(placeName)
                .onStart { _uiState.emit(UiState.Loading) }
                .cancellable()
                .collect { result ->
                    result
                        .onSuccess { searchPlaceResult ->
                            _uiState.emit(UiState.Success(searchPlaceResult))
                        }
                        .onFailure { throwable ->
                            _uiState.emit(UiState.Failure(throwable.stackTrace.toString()))
                        }
                }
        }
    }
}

