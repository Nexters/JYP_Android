package com.jyp.jyp_design.ui.button

import androidx.compose.ui.graphics.Color
import com.jyp.jyp_design.resource.JypColors
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

enum class ButtonColorSetType(
    val backgroundColor: Color,
    val textColor: Color
) {
    PINK(
        backgroundColor = JypColors.Pink,
        textColor = JypColors.Text_white
    ),
    BLACK(
        backgroundColor = JypColors.Sub_black,
        textColor = JypColors.Text_white
    ),
    YELLOW(
        backgroundColor = JypColors.Yellow,
        textColor = JypColors.Text75
    ),
    GRAY(
        backgroundColor = JypColors.Tag_white_grey100,
        textColor = JypColors.Text40
    )
}