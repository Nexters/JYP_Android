package com.jyp.jyp_design.ui.typography.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.type.TextType


fun TextStyle(
    type: TextType,
    textAlign: TextAlign,
    color: Color
): TextStyle {

    return TextStyle(
        fontFamily = type.fontFamily,
        fontWeight = type.fontWeight,
        fontSize = type.fontSize.sp,
        lineHeight = (type.fontSize * type.lineHeight).sp,
        textAlign = textAlign,
        color = color
    )
}