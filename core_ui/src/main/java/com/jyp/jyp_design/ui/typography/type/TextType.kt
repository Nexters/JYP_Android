package com.jyp.jyp_design.ui.typography.type

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.jyp.jyp_design.ui.typography.font.PretendardFontFamily


enum class TextType(
    val fontFamily: FontFamily,
    val fontWeight: FontWeight,
    val fontSize: Int,
    val lineHeight: Double
) {
    HEADING_1(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22,
        lineHeight = 1.55
    ),
    HEADING_2(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20,
        lineHeight = 1.5
    ),
    HEADING_3(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16,
        lineHeight = 1.5
    ),
    TITLE_1(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24,
        lineHeight = 1.5
    ),
    TITLE_2(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20,
        lineHeight = 1.5
    ),
    TITLE_3(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18,
        lineHeight = 1.5
    ),
    TITLE_4(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18,
        lineHeight = 1.5
    ),
    TITLE_5(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17,
        lineHeight = 1.5
    ),
    TITLE_6(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16,
        lineHeight = 1.5
    ),
    BODY_1(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16,
        lineHeight = 1.5
    ),
    BODY_2(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16,
        lineHeight = 1.5
    ),
    BODY_3(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14,
        lineHeight = 1.5
    ),
    BODY_4(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12,
        lineHeight = 1.5
    ),
    TAG_1(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16,
        lineHeight = 1.5
    ),
    TAG_2(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14,
        lineHeight = 1.5
    )
}