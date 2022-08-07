package com.jyp.feature_my_journey.presentation.my_journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyJourneyViewModel @Inject constructor(
        // use case..
) : ViewModel() {
    private val _plannedJourneys = MutableStateFlow(listOf<String>())
    val plannedJourneys: StateFlow<List<String>>
        get() = _plannedJourneys

    private val _pastJourneys = MutableStateFlow(listOf<String>())
    val pastJourneys: StateFlow<List<String>>
        get() = _pastJourneys

    fun fetchJourneyList() {
        viewModelScope.launch {
            _plannedJourneys.emit(listOf("강릉여행기", "즐거운여행기"))
        }
    }
}
