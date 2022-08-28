package com.jyp.feature_planner.domain

import com.jyp.jyp_design.ui.tag.TagState
import com.jyp.jyp_design.ui.tag.TagType

data class Tag(
        val type: TagType,
        val content: String,
        val state: TagState = TagState.DEFAULT,
        val selectPeople: List<Person> = emptyList(),
)
