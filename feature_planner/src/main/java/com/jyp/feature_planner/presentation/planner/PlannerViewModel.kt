package com.jyp.feature_planner.presentation.planner

import androidx.lifecycle.ViewModel
import com.jyp.feature_planner.domain.PikMe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor() : ViewModel() {
    private val _pikMes = MutableStateFlow<List<PikMe>>(emptyList())
    val pikMes: StateFlow<List<PikMe>>
        get() = _pikMes
}
