package com.jyp.feature_planner.presentation.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_planner.domain.*
import com.jyp.feature_planner.presentation.planner.model.PlanItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val getPlannerUseCase: GetPlannerUseCase,
) : ViewModel() {
    private val _pikMes = MutableStateFlow<List<PikMe>>(emptyList())
    val pikMes: StateFlow<List<PikMe>>
        get() = _pikMes

    private val _tags = MutableStateFlow<List<PlannerTag>>(emptyList())
    val tags: StateFlow<List<PlannerTag>>
        get() = _tags

    private val _membersProfileUrl = MutableStateFlow<List<String>>(emptyList())
    val membersProfileUrl: StateFlow<List<String>>
        get() = _membersProfileUrl

    private val _planItems = MutableStateFlow<List<PlanItem>>(emptyList())
    val planItems: StateFlow<List<PlanItem>>
        get() = _planItems

    private val _plannerDates = MutableStateFlow(Pair("", ""))
    val plannerDates: StateFlow<Pair<String, String>>
        get() = _plannerDates

    fun fetchPlannerData(id: String) {
        viewModelScope.launch {
            getPlannerUseCase.invoke(id)
                .onSuccess { planner ->
                    val tagMapper = PlannerTagMapper()
                    val pikiMapper = PlannerPikiMapper()

                    _tags.value = planner.tags.map(tagMapper::toPlannerTag)
                    _membersProfileUrl.value = planner.users.map { it.profileImagePath }

                    _planItems.value = planner.pikidays.mapIndexed { index, pikiDay ->
                        PlanItem(index + 1, pikiDay.pikis.map(pikiMapper::toPlannerPiki))
                    }

                    _plannerDates.value = Pair(
                        SimpleDateFormat("M월 d일", Locale.getDefault()).format(planner.startDate * 1000),
                        SimpleDateFormat("M월 d일", Locale.getDefault()).format(planner.endDate * 1000),
                    )
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                }
        }
    }

    fun fetchPikMes() {
        _pikMes.value = listOf(
            PikMe(
                title = "아르떼 뮤지엄",
                address = "강원 강릉시 난설헌로 131",
                category = "문화시설",
                likeCount = 0,
            )
        )
    }
}
