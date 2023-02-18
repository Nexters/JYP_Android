package com.jyp.feature_my_journey.presentation.my_journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_my_journey.domain.GetJourneysUseCase
import com.jyp.feature_my_journey.domain.LeaveJourneyUseCase
import com.jyp.feature_my_journey.domain.GetMeUseCase
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
    private val getMeUseCase: GetMeUseCase,
    private val getJourneysUseCase: GetJourneysUseCase,
    private val leaveJourneyUseCase: LeaveJourneyUseCase
) : ViewModel() {
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String>
        get() = _userName

    private val _personality = MutableStateFlow("")
    val personality: StateFlow<String>
        get() = _personality

    private val _plannedJourneys = MutableStateFlow(listOf<Journey>())
    val plannedJourneys: StateFlow<List<Journey>>
        get() = _plannedJourneys

    private val _pastJourneys = MutableStateFlow(listOf<Journey>())
    val pastJourneys: StateFlow<List<Journey>>
        get() = _pastJourneys

    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition


    fun fetchUser() {
        viewModelScope.launch {
            getMeUseCase.invoke()
                .onSuccess { user ->
                    _userName.value = user.name
                    _personality.value = user.personality
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun fetchJourneyList() {
        viewModelScope.launch {
            getJourneysUseCase()
                .onSuccess { response ->
                    _plannedJourneys.value = response.journeys.map { journey ->
                        val startDay = SimpleDateFormat("d", Locale.getDefault())
                            .format(Date(journey.startDate * 1000))
                            .toInt()

                        val today = SimpleDateFormat("d", Locale.getDefault())
                            .format(Date(System.currentTimeMillis()))
                            .toInt()

                        val dDay = (startDay - today).takeIf { gap ->
                            gap > 0
                        } ?: "day"

                        Journey(
                            id = journey.id,
                            dDay = "D-$dDay",
                            title = journey.name,
                            themeType = ThemeType.values()
                                .firstOrNull { themeType -> themeType.imagePath == journey.themePath }
                                ?: ThemeType.DEFAULT,
                            startDay = SimpleDateFormat("M월 d일", Locale.getDefault()).format(
                                Date(
                                    journey.startDate * 1000
                                )
                            ),
                            endDay = SimpleDateFormat("M월 d일", Locale.getDefault()).format(
                                Date(
                                    journey.endDate * 1000
                                )
                            ),
                            profileUrls = journey.users.map { it.profileImagePath },
                        )
                    }
                }
        }
    }

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }

    fun leaveJourney(journeyId: String) {
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
}
