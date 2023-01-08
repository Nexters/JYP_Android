package com.jyp.feature_planner.presentation.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_planner.domain.GetPlannerUseCase
import com.jyp.feature_planner.domain.PikMe
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.domain.PlannerTagMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    fun fetchPlannerData(id: String) {
        viewModelScope.launch {
            getPlannerUseCase.invoke(id)
                .onSuccess { planner ->
                    val tagMapper = PlannerTagMapper()

                    _tags.value = planner.tags.map(tagMapper::toPlannerTag)
                    _membersProfileUrl.value = planner.users.map { it.profileImagePath }
                }
                .onFailure {
                    it.printStackTrace()
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
