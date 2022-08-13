package com.jyp.jyp_design.ui.button

import com.jyp.jyp_design.ui.typography.type.TextType


enum class ButtonType(
    val verticalPadding: Int,
    val textType: TextType
) {
    THICK(
        verticalPadding = 14,
        textType = TextType.TITLE_6
    ),
    MEDIUM(
        verticalPadding = 13,
        textType = TextType.BODY_1
    ),
    THIN(
        verticalPadding = 8,
        textType = TextType.BODY_1
    )
}