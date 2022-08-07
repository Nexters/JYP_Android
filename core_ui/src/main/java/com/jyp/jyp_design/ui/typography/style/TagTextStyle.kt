package com.jyp.jyp_design.ui.typography.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily
import com.jyp.jyp_design.ui.typography.type.TagType


fun tagTextStyle(
    type: TagType,
    textAlign: TextAlign,
    color: Color
) = when (type) {
    TagType.TAG_1 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TagType.TAG_2 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
}