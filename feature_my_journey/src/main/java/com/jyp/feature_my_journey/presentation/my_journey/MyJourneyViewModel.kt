package com.jyp.feature_my_journey.presentation.my_journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.feature_my_journey.domain.Journey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyJourneyViewModel @Inject constructor(
        // use case..
) : ViewModel() {
    private val _plannedJourneys = MutableStateFlow(listOf<Journey>())
    val plannedJourneys: StateFlow<List<Journey>>
        get() = _plannedJourneys

    private val _pastJourneys = MutableStateFlow(listOf<Journey>())
    val pastJourneys: StateFlow<List<Journey>>
        get() = _pastJourneys

    fun fetchJourneyList() {
        viewModelScope.launch {
            _plannedJourneys.value = listOf(
                    Journey(
                            dDay = "D-3",
                            title = "강릉여행기",
                            theme = 0,
                            startDay = "8월 23일",
                            endDay = "8월 25일",
                            profileUrls = emptyList()
                    ),
                    Journey(
                            dDay = "D-8",
                            title = "즐거운여행기",
                            theme = 1,
                            startDay = "8월 28일",
                            endDay = "8월 30일",
                            profileUrls = emptyList()
                    ),
                    Journey(
                            dDay = "D-31",
                            title = "행복한여행기",
                            theme = 2,
                            startDay = "9월 20일",
                            endDay = "9월 25일",
                            profileUrls = emptyList()
                    ),
                    Journey(
                            dDay = "D-40",
                            title = "점잖은여행기",
                            theme = 3,
                            startDay = "9월 29일",
                            endDay = "10월 25일",
                            profileUrls = emptyList()
                    ),
            )
        }
    }
}
