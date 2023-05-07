package com.jyp.feature_planner.domain

import com.jyp.jyp_design.ui.tag.TagState
import com.jyp.jyp_design.ui.tag.TagType

data class PlannerTag(
        val type: TagType,
        val content: String,
        var state: TagState = TagState.DEFAULT,
        val selectPeople: List<Person> = emptyList(),
)
