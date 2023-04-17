package com.jyp.jyp_design.ui.avatar

enum class AvatarType(
    val size: Int,
    val radius: Int
) {
    SMALL(
        size = 44,
        radius = 12
    ),
    MEDIUM(
        size = 70,
        radius = 20
    ),
    LARGE(
        size = 88,
        radius = 24
    )
}