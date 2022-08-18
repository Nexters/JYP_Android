package com.jyp.feature_planner.presentation.create_planner

import androidx.lifecycle.ViewModel
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.ui.tag.TagState
import com.jyp.jyp_design.ui.tag.TagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CreatePlannerViewModel @Inject constructor() : ViewModel() {
    private val _startDateMillis = MutableStateFlow(0L)
    val startDateMillis: StateFlow<Long>
        get() = _startDateMillis

    private val _endDateMillis = MutableStateFlow(0L)
    val endDateMillis: StateFlow<Long>
        get() = _endDateMillis

    private val _tags = MutableStateFlow(
            listOf(
                    Tag(type = TagType.Soso(), content = "모두 찬성"),
                    Tag(type = TagType.Soso(), content = "상관없어"),
                    Tag(type = TagType.Like(), content = "좋아1"),
                    Tag(type = TagType.Like(), content = "좋아2"),
                    Tag(type = TagType.Like(), content = "좋아3"),
                    Tag(type = TagType.Like(), content = "좋아4"),
                    Tag(type = TagType.Like(), content = "좋아5"),
                    Tag(type = TagType.Like(), content = "좋아6"),
                    Tag(type = TagType.Like(), content = "좋아7"),
                    Tag(type = TagType.Dislike(), content = "싫어1"),
                    Tag(type = TagType.Dislike(), content = "싫어2"),
                    Tag(type = TagType.Dislike(), content = "싫어3"),
                    Tag(type = TagType.Dislike(), content = "싫어4"),
            )
    )
    val tags: StateFlow<List<Tag>>
        get() = _tags

    fun updateDate(startMillis: Long, endMillis: Long) {
        _startDateMillis.value = startMillis
        _endDateMillis.value = endMillis
    }

    fun clickTag(tag: Tag) {
        val clickIndex = tags.value.indexOf(tag)
        val tagState = tag.state
        val newTag = tag.copy(
                state = when (tagState) {
                    TagState.DEFAULT -> TagState.SELECTED
                    TagState.SELECTED -> TagState.DEFAULT
                    TagState.DISABLED -> TagState.DISABLED
                }
        )

        val newList = tags.value.toMutableList()

        newList[clickIndex] = newTag

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

        _tags.value = newList
    }
}
