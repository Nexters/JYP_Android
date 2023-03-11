package com.jyp.feature_planner.presentation.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.model.Pikme
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
    private val getMeUseCase: GetMeUseCase,
    private val getPlannerUseCase: GetPlannerUseCase,
    private val setPikmeLikeUseCase: SetPikmeLikeUseCase,
) : ViewModel() {
    private val _plannerTitle = MutableStateFlow("")
    val plannerTitle: StateFlow<String>
        get() = _plannerTitle

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

    private val _plannerDates = MutableStateFlow(Pair(0L, 0L))
    val plannerDates: StateFlow<Pair<Long, Long>>
        get() = _plannerDates

    fun fetchPlannerData(id: String) {
        viewModelScope.launch {
            var pikmis: List<Pikme> = emptyList()

            getPlannerUseCase.invoke(id)
                .onSuccess { planner ->
                    val tagMapper = PlannerTagMapper()
                    val pikiMapper = PlannerPikiMapper()

                    _plannerTitle.value = planner.name

                    _tags.value = planner.tags.map(tagMapper::toPlannerTag)
                    _membersProfileUrl.value = planner.users.map { it.profileImagePath }

                    _planItems.value = planner.pikidays.mapIndexed { index, pikiDay ->
                        PlanItem(index + 1, pikiDay.pikis.map(pikiMapper::toPlannerPiki))
                    }

                    _plannerDates.value = Pair(
                        planner.startDate,
                        planner.endDate,
                    )

                    pikmis = planner.pikmis
                }
                .onFailure { failure ->
//                    exception.printStackTrace()
                }

            val pikmeMapper = PlannerPikmeMapper()

            getMeUseCase.invoke()
                .onSuccess { user ->
                    _pikmis.value = pikmis.map { pikme ->
                        pikmeMapper.toPlannerPikme(pikme, user.id)
                    }
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun switchPikmeLike(plannerId: String, pikme: PlannerPikme) {
        viewModelScope.launch {
            val isLike = !pikme.liked

            setPikmeLikeUseCase.invoke(plannerId, pikme.id, isLike)
                .onSuccess {

                }
                .onFailure {
                    it.printStackTrace()
                }

            // data 가 null인 케이스를 처리하지 못해서 임시방편으로 onSuccess에 넣지 못했음
            val pikmeIndex = pikmis.value.indexOf(pikme)

            _pikmis.value = pikmis.value.toMutableList()
                .apply {
                    set(
                        pikmeIndex,
                        pikme.copy(
                            likeCount = pikme.likeCount + if (isLike) {
                                1
                            } else {
                                -1
                            },
                            liked = isLike,
                        )
                    )
                }
        }
    }
}
