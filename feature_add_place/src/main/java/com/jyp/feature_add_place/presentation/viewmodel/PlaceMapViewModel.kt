package com.jyp.feature_add_place.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.domain.AddPikmeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceMapViewModel @Inject constructor(
    private val addPikmeUseCase: AddPikmeUseCase,
) : ViewModel() {

    private val _addPikmeUiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val addPikmeUiState = _addPikmeUiState.asStateFlow()


    fun addPikme(plannerId: String, searchPlaceResultModel: SearchPlaceResultModel) {
        _addPikmeUiState.value = UiState.Loading
        viewModelScope.launch {
            addPikmeUseCase(plannerId, searchPlaceResultModel)
                .onSuccess { response ->
                    _addPikmeUiState.value = UiState.Success(response)
                }
                .onFailure { throwable ->
                    _addPikmeUiState.value = UiState.Failure(throwable)
                }
        }
    }
}
