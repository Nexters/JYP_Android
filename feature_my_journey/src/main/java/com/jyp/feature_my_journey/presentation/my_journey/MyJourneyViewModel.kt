package com.jyp.feature_my_journey.presentation.my_journey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_my_journey.domain.GetJourneysUseCase
import com.jyp.feature_my_journey.domain.LeaveJourneyUseCase
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.jyp_design.enumerate.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyJourneyViewModel @Inject constructor(
    private val getJourneysUseCase: GetJourneysUseCase,
    private val leaveJourneyUseCase: LeaveJourneyUseCase
) : ViewModel() {
    private val _plannedJourneys = MutableStateFlow(listOf<Journey>())
    val plannedJourneys: StateFlow<List<Journey>>
        get() = _plannedJourneys

    private val _pastJourneys = MutableStateFlow(listOf<Journey>())
    val pastJourneys: StateFlow<List<Journey>>
        get() = _pastJourneys

    fun fetchJourneyList() {
        viewModelScope.launch {
            getJourneysUseCase()
                .onSuccess { response ->

                    val currentTimeMillis = System.currentTimeMillis()

                    val filteredPlannedJourneys = response.journeys.filter { journey ->
                        val endTimeMillis = journey.endDate * 1000
                        currentTimeMillis <= endTimeMillis
                    }

                    val filteredPastJourneys = response.journeys.filter { journey ->
                        val endTimeMillis = journey.endDate * 1000
                        endTimeMillis < currentTimeMillis
                    }

                    _plannedJourneys.value = mappingJourneyList(
                        filteredPlannedJourneys,
                        false
                    )
                    _pastJourneys.value = mappingJourneyList(
                        filteredPastJourneys,
                        true
                    )
                }

                .onFailure { throwable ->
                    throwable.printStackTrace()
                }
        }
    }

    fun leaveJourney(journeyId: String) {
        Log.d("leave journey", "$journeyId")
        viewModelScope.launch {
            leaveJourneyUseCase.invoke(journeyId)
                .onSuccess { _ ->
                    fetchJourneyList()
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                }
        }
    }

    private fun mappingJourneyList(
        journeys: List<com.jyp.core_network.jyp.model.Journey>,
        isPastJourney: Boolean
    ): List<Journey> {

        val today = SimpleDateFormat("d", Locale.getDefault())
            .format(Date(System.currentTimeMillis()))
            .toInt()

        val dateFormat = when (isPastJourney) {
            true -> "yy. MM. dd"
            false -> "M월 d일"
        }

        return journeys.map { journey ->
            Journey(
                id = journey.id,
                dDay = when (isPastJourney) {
                    true -> ""
                    false -> {
                        val startDate = SimpleDateFormat("d", Locale.getDefault())
                            .format(Date(journey.startDate * 1000))
                            .toInt()
                        val leftDay = startDate - today
                        if (leftDay > 0) "D-$leftDay" else "D-day"
                    }
                },
                title = journey.name,
                themeType = ThemeType.values().firstOrNull { themeType ->
                    themeType.imagePath == journey.themePath
                } ?: ThemeType.DEFAULT,
                startDay = SimpleDateFormat(dateFormat, Locale.getDefault()).format(
                    Date(journey.startDate * 1000)
                ),
                endDay = SimpleDateFormat(dateFormat, Locale.getDefault()).format(
                    Date(journey.endDate * 1000)
                ),
                profileUrls = journey.users.map { user -> user.profileImagePath }
            )
        }
    }
}
