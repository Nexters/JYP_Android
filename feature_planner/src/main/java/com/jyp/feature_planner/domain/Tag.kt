package com.jyp.feature_planner.domain

import com.jyp.jyp_design.ui.tag.TagType

data class Tag(
        val type: TagType,
        val content: String,
)
