package com.jyp.feature_planner.presentation.add_planner_route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jyp.core_network.jyp.model.enumerate.toPlaceCategory
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.domain.PlannerPikme
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddPlannerRouteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _pikmis: MutableStateFlow<List<PlannerPikme>> =
        MutableStateFlow(savedStateHandle[AddPlannerRouteActivity.EXTRA_PIKMIS] ?: emptyList())

    val pikmis: StateFlow<List<PlannerPikme>>
        get() = _pikmis

    private val _pikis: MutableStateFlow<List<PlannerPiki>> =
        MutableStateFlow(savedStateHandle[AddPlannerRouteActivity.EXTRA_PIKIS] ?: emptyList())

    val pikis: StateFlow<List<PlannerPiki>>
        get() = _pikis

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
}
