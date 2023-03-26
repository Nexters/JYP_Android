package com.jyp.feature_planner.presentation.create_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_planner.domain.*
import com.jyp.jyp_design.ui.tag.TagState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlannerViewModel @Inject constructor(
    private val createPlannerUseCase: CreatePlannerUseCase,
    private val joinPlannerUseCase: JoinPlannerUseCase,
    private val getDefaultTagsUseCase: GetDefaultTagsUseCase,
    private val getTagsUseCase: GetTagsUseCase,
) : ViewModel() {

    private val _startDateMillis = MutableStateFlow(0L)
    val startDateMillis: StateFlow<Long>
        get() = _startDateMillis

    private val _endDateMillis = MutableStateFlow(0L)
    val endDateMillis: StateFlow<Long>
        get() = _endDateMillis

    private val _joinPlannerUiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val joinPlannerUiState = _joinPlannerUiState.asStateFlow()

    private val _tags = MutableStateFlow<List<PlannerTag>>(listOf())
    val tags: StateFlow<List<PlannerTag>>
        get() = _tags

    init {
        viewModelScope.launch {
            getDefaultTagsUseCase.invoke()
                .onSuccess {
                    val mapper = PlannerTagMapper()

                    _tags.value = it.tags.map(mapper::toPlannerTag)
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun updateDate(startMillis: Long, endMillis: Long) {
        _startDateMillis.value = startMillis
        _endDateMillis.value = endMillis
    }

    fun fetchTags(plannerId: String) {
        viewModelScope.launch {
            getTagsUseCase.invoke(plannerId)
                .onSuccess {
                    val mapper = PlannerTagMapper()

                    _tags.value += it.tags.map(mapper::toPlannerTag)
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun addTag(tag: PlannerTag) {
        val newList = tags.value + tag

        updateTagState(newList)

        _tags.value = newList
    }

    fun clickTag(tag: PlannerTag) {
        val clickIndex = tags.value.indexOf(tag)
        val tagState = tag.state
        val newTag = tag.copy(
            state = when (tagState) {
                TagState.DEFAULT -> TagState.SELECTED
                TagState.SELECTED -> TagState.DEFAULT
                TagState.DISABLED -> TagState.DISABLED
            }
        )

        val mutableTags = tags.value.toMutableList()
        mutableTags[clickIndex] = newTag

        _tags.value = updateTagState(mutableTags)
    }

    private fun updateTagState(list: List<PlannerTag>): List<PlannerTag> {
        val newList = list.toMutableList()

        val clickedTagCount = newList.count { it.state == TagState.SELECTED }
        if (clickedTagCount >= 3) {
            repeat(newList.size) { i ->
                if (newList[i].state == TagState.DEFAULT) {
                    newList[i] = newList[i].copy(state = TagState.DISABLED)
                }
            }
        } else {
            repeat(newList.size) { i ->
                if (newList[i].state == TagState.DISABLED) {
                    newList[i] = newList[i].copy(state = TagState.DEFAULT)
                }
            }
        }

        return newList
    }

    fun createPlanner(
        title: String,
        themeType: String,
        startMillis: Long,
        endMillis: Long,
        tags: List<PlannerTag>
    ) {
        viewModelScope.launch {
            createPlannerUseCase.invoke(
                title,
                startMillis / 1000,
                endMillis / 1000,
                themeType,
                tags,
            )
        }
    }

    fun joinPlanner(
        plannerId: String,
        tags: List<PlannerTag>
    ) {
        viewModelScope.launch {
            joinPlannerUseCase(plannerId, tags)
                .onSuccess {
                    _joinPlannerUiState.value = UiState.Success(plannerId)
                }
                .onFailure { throwable ->
                    _joinPlannerUiState.value = UiState.Failure(throwable)
                }
        }
    }
}
