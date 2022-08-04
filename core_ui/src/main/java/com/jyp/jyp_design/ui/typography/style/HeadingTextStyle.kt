package com.jyp.jyp_design.ui.typography.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily
import com.jyp.jyp_design.ui.typography.type.HeadingType


@Composable
fun headingTextStyle(
    type: HeadingType,
    textAlign: TextAlign,
    color: Color
) = when (type) {
    HeadingType.HEADING_1 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    HeadingType.HEADING_2 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    HeadingType.HEADING_3 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
}