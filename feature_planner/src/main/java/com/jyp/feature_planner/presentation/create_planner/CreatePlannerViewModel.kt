package com.jyp.feature_planner.presentation.create_planner

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CreatePlannerViewModel @Inject constructor() : ViewModel() {
    private val _startDateMillis = MutableStateFlow(0L)
    val startDateMillis: StateFlow<Long>
        get() = _startDateMillis

    private val _endDateMillis = MutableStateFlow(0L)
    val endDateMillis: StateFlow<Long>
        get() = _endDateMillis

    fun updateDate(startMillis: Long, endMillis: Long) {
        _startDateMillis.value = startMillis
        _endDateMillis.value = endMillis
    }
}
