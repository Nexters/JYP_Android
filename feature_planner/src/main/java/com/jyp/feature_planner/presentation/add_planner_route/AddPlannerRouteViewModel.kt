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
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddPlannerRouteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setPikisUseCase: SetPikisUseCase,
) : ViewModel() {
    private val journeyId: String? = savedStateHandle[AddPlannerRouteActivity.EXTRA_JOURNEY_ID]
    val dayIndex: Int = savedStateHandle[AddPlannerRouteActivity.EXTRA_DAY_INDEX] ?: 0

    private val _pikmis =
        savedStateHandle.get<List<PlannerPikme>>(AddPlannerRouteActivity.EXTRA_PIKMIS)
            ?.let { plannerPikmes ->
                val sortedPimes = plannerPikmes.sortedByDescending { it.likeCount }.toMutableList()

                var ranking = 0

                for (i in sortedPimes.indices) {
                    val pikme = sortedPimes[i]

                    val beforeLiked = sortedPimes.getOrNull(i - 1)?.likeCount ?: 0

                    if (pikme.likeCount > beforeLiked) {
                        ranking++
                    }

                    if (pikme.likeCount > 0) {
                        sortedPimes[i] = pikme.copy(ranking = ranking)
                    } else {
                        break
                    }
                }

                sortedPimes
            }
            .orEmpty()
            .let {
                MutableStateFlow(it)
            }

    val pikmis: StateFlow<List<PlannerPikme>>
        get() = _pikmis

    private val _pikis: MutableStateFlow<List<PlannerPiki>> =
        MutableStateFlow(savedStateHandle[AddPlannerRouteActivity.EXTRA_PIKIS] ?: emptyList())

    val pikis: StateFlow<List<PlannerPiki>>
        get() = _pikis

    private val _currentDate =
        MutableStateFlow(
            (savedStateHandle.get<Long>(
                AddPlannerRouteActivity.EXTRA_START_DATE
            ) ?: 0) + TimeUnit.DAYS.toSeconds(dayIndex.toLong())
        )

    val currentDate: StateFlow<Long>
        get() = _currentDate

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

    fun removePiki(index: Int) {
        val mutablePikis = _pikis.value.toMutableList()
        mutablePikis.removeAt(index)
        _pikis.value = mutablePikis
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
