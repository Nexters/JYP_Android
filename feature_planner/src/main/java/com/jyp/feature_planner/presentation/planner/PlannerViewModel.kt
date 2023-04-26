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
                .onFailure { throwable ->
                    throwable.printStackTrace()
                }

            val pikmeMapper = PlannerPikmeMapper()

            getMeUseCase.invoke()
                .onSuccess { user ->
                    _pikmis.value = pikmis.let { pikmis ->
                        var ranking = 0
                        val sortedPikmes = pikmis.sortedByDescending { it.likeBy.size }
                            .map { pikmeMapper.toPlannerPikme(it, user.id) }
                            .toMutableList()

                        for (i in sortedPikmes.indices) {
                            val currentPikme = sortedPikmes[i]
                            if (currentPikme.likeCount > 0) {

                                val previousPikmeLikedCount = sortedPikmes.getOrNull(i - 1)?.likeCount ?: 0
                                if (currentPikme.likeCount > previousPikmeLikedCount) {
                                    ranking++
                                }
                                sortedPikmes[i] = currentPikme.copy(ranking = ranking)

                            } else {
                                break
                            }
                        }
                        sortedPikmes
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
                    fetchPlannerData(plannerId)
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }
}
