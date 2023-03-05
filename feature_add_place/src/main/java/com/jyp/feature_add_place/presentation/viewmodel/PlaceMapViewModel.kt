package com.jyp.feature_add_place.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.domain.AddPikmeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceMapViewModel @Inject constructor(
    private val addPikmeUseCase: AddPikmeUseCase,
) : ViewModel() {
    fun addPikme(plannerId: String, searchPlaceResultModel: SearchPlaceResultModel) {
        viewModelScope.launch {
            addPikmeUseCase(plannerId, searchPlaceResultModel)
                .onSuccess { response ->
                    Log.d("addPikMe", response.toString())
                }
                .onFailure { failure ->
//                    e.printStackTrace()
                }
        }
    }
}
