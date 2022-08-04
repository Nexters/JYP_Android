package com.jyp.jyp_design.ui.typography.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily
import com.jyp.jyp_design.ui.typography.type.TitleType


@Composable
fun titleTextStyle(
    type: TitleType,
    textAlign: TextAlign,
    color: Color
) = when (type) {
    TitleType.TITLE_1 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TitleType.TITLE_2 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TitleType.TITLE_3 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TitleType.TITLE_4 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TitleType.TITLE_5 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    TitleType.TITLE_6 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
}