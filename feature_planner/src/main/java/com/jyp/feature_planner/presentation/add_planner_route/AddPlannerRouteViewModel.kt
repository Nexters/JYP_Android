package com.jyp.feature_planner.presentation.add_planner_route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.feature_planner.domain.SetPikisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlannerRouteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setPikisUseCase: SetPikisUseCase,
) : ViewModel() {
    private val _pikmis: MutableStateFlow<List<PlannerPikme>> =
        MutableStateFlow(savedStateHandle[AddPlannerRouteActivity.EXTRA_PIKMIS] ?: emptyList())

    val pikmis: StateFlow<List<PlannerPikme>>
        get() = _pikmis

    private val _pikis: MutableStateFlow<List<PlannerPiki>> =
        MutableStateFlow(savedStateHandle[AddPlannerRouteActivity.EXTRA_PIKIS] ?: emptyList())

    val pikis: StateFlow<List<PlannerPiki>>
        get() = _pikis

    private val journeyId: String? = savedStateHandle[AddPlannerRouteActivity.EXTRA_JOURNEY_ID]
    private val dayIndex: Int? = savedStateHandle[AddPlannerRouteActivity.EXTRA_DAY_INDEX]

    fun addPiki(pikme: PlannerPikme) {
        _pikis.value = pikis.value + PlannerPiki(
            id = null,
            name = pikme.title,
            address = pikme.address,
            category = pikme.category,
            longitude = pikme.longitude,
            latitude = pikme.latitude,
            link = pikme.link,
        )
    }

    fun setPikis() {
        viewModelScope.launch {
            setPikisUseCase.invoke(
                journeyId ?: return@launch,
                dayIndex ?: return@launch,
                pikis.value,
            ).onSuccess {

            }.onFailure {

            }
        }
    }
}
