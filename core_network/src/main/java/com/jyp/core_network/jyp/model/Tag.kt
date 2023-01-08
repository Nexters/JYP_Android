package com.jyp.core_network.jyp.model

import com.jyp.core_network.jyp.model.enumerate.TagOrientation

data class Tag(
        val topic: String,
        val orientation: TagOrientation,
        val users: List<User>,
)
