package com.jyp.jyp_design.ui.typography.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily
import com.jyp.jyp_design.ui.typography.type.BodyType


@Composable
fun bodyTextStyle(
    type: BodyType,
    textAlign: TextAlign,
    color: Color
) = when (type) {
    BodyType.BODY_1 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    BodyType.BODY_2 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    BodyType.BODY_3 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
    BodyType.BODY_4 -> TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 1.5.sp,
        textAlign = textAlign,
        color = color
    )
}