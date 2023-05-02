package com.jyp.feature_planner.presentation.edit_tag

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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditTagViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val editTagsUseCase: EditTagsUseCase
) : ViewModel() {

    private lateinit var user: Person
    private lateinit var plannerId: String

    private val _entireTags = MutableStateFlow(listOf<PlannerTag>())
    val entireTags: StateFlow<List<PlannerTag>>
        get() = _entireTags

    private val _selectedTags = MutableStateFlow(listOf<PlannerTag>())

    private val _editTagsUiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val editTagsUiState: StateFlow<UiState<*>>
        get() = _editTagsUiState


    fun getTags(
        user: Person,
        plannerId: String
    ) {
        this.user = user
        this.plannerId = plannerId

        viewModelScope.launch {
            getTagsUseCase.invoke(plannerId, true)
                .onSuccess { response ->
                    val mapper = PlannerTagMapper()
                    val responseTags = response.tags
                        .map(mapper::toPlannerTag)
                        .map {
                            if (it.selectPeople.contains(user)) {
                                it.state = TagState.SELECTED
                            }
                            it
                        }
                    _entireTags.value = updateTagState(responseTags)
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                }
        }
    }

    fun addTagToEntireTags(tag: PlannerTag) {
        _entireTags.value += tag
        _entireTags.value = updateTagState(_entireTags.value)
    }

    fun clickTag(tag: PlannerTag) {
        val clickIndex = entireTags.value.indexOf(tag)
        val tagState = tag.state
        val newTag = tag.copy(
            state = when (tagState) {
                TagState.DEFAULT -> TagState.SELECTED
                TagState.SELECTED -> TagState.DEFAULT
                TagState.DISABLED -> TagState.DISABLED
            }
        )

        val mutableTags = entireTags.value.toMutableList()
        mutableTags[clickIndex] = newTag

        _entireTags.value = updateTagState(mutableTags)
        _selectedTags.value = _entireTags.value
            .filter { it.state == TagState.SELECTED }
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

    fun editTags() {
        viewModelScope.launch {
            editTagsUseCase.invoke(
                plannerId,
                _selectedTags.value

            ).onSuccess { response ->
                _editTagsUiState.value = UiState.Success(response)

            }.onFailure { throwable ->
                _editTagsUiState.value = UiState.Failure(throwable)
            }
        }
    }
}