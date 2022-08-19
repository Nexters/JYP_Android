package com.jyp.feature_planner.presentation.planner

import androidx.lifecycle.ViewModel
import com.jyp.feature_planner.domain.PikMe
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.ui.tag.TagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor() : ViewModel() {
    private val _pikMes = MutableStateFlow<List<PikMe>>(emptyList())
    val pikMes: StateFlow<List<PikMe>>
        get() = _pikMes

    private val _tags = MutableStateFlow(
            listOf(
                    Tag(type = TagType.Like(), content = "시러시러"),
                    Tag(type = TagType.Like(), content = "시러허허"),
                    Tag(type = TagType.Like(), content = "싫어싫"),
                    Tag(type = TagType.Dislike(), content = "조아아"),
                    Tag(type = TagType.Dislike(), content = "좋아"),
                    Tag(type = TagType.Dislike(), content = "시러머버더거서ㅛㅓ"),
                    Tag(type = TagType.Soso(), content = "상관업"),
            )
    )
    val tags: StateFlow<List<Tag>>
        get() = _tags
}
