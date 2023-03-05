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
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val getPlannerUseCase: GetPlannerUseCase,
    private val setPikmeLikeUseCase: SetPikmeLikeUseCase,
) : ViewModel() {
    private val _pikmis = MutableStateFlow<List<PlannerPikme>>(emptyList())
    val pikmis: StateFlow<List<PlannerPikme>>
        get() = _pikmis

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
                    val pikmeMapper = PlannerPikmeMapper()

                    _tags.value = planner.tags.map(tagMapper::toPlannerTag)
                    _membersProfileUrl.value = planner.users.map { it.profileImagePath }

                    _planItems.value = planner.pikidays.mapIndexed { index, pikiDay ->
                        PlanItem(index + 1, pikiDay.pikis.map(pikiMapper::toPlannerPiki))
                    }

                    _plannerDates.value = Pair(
                        SimpleDateFormat("M월 d일", Locale.getDefault()).format(planner.startDate * 1000),
                        SimpleDateFormat("M월 d일", Locale.getDefault()).format(planner.endDate * 1000),
                    )

                    _pikmis.value = planner.pikmis.map(pikmeMapper::toPlannerPikme)
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                }
        }
    }

    fun setPikmeLike(plannerId: String, pikmeId: String, isLike: Boolean) {
        viewModelScope.launch {
            setPikmeLikeUseCase.invoke(plannerId, pikmeId, isLike)
                .onSuccess {

                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }
}
