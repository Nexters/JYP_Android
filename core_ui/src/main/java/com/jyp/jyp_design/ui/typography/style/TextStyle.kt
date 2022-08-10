package com.jyp.jyp_design.ui.typography.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily
import com.jyp.jyp_design.ui.typography.type.TextType


fun TextStyle(
    type: TextType,
    textAlign: TextAlign,
    color: Color
): TextStyle {

    return when (type) {
        TextType.HEADING_1 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.HEADING_2 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.HEADING_3 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )

        TextType.TITLE_1 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TITLE_2 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TITLE_3 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TITLE_4 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TITLE_5 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TITLE_6 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )

        TextType.BODY_1 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.BODY_2 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.BODY_3 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.BODY_4 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )

        TextType.TAG_1 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
        TextType.TAG_2 -> TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 1.5.sp,
            textAlign = textAlign,
            color = color
        )
    }
}