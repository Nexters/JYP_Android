package com.jyp.feature_planner.presentation.create_planner

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.enumerate.ThemeType
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun PlannerThemeSelectBottomSheetScreen(
        onSubmitTheme: (ThemeType) -> Unit,
) {
    var selectedTheme by remember {
        mutableStateOf<ThemeType?>(null)
    }

    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                            top = 28.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 28.dp,
                    ),
    ) {
        Header()
        Spacer(modifier = Modifier.size(24.dp))
        ThemeList(
                selectedTheme = selectedTheme,
                onSelectTheme = {
                    selectedTheme = it
                },
        )
        Spacer(modifier = Modifier.size(40.dp))
        Footer(
                selectedTheme = selectedTheme,
                onSubmitTheme = onSubmitTheme,
        )
    }
}

@Composable
private fun Header() {
    Column {
        JypText(
                text = "여행기 커버 이미지",
                type = TextType.HEADING_2,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = "여행 테마에 맞게 선택해주세요!",
                type = TextType.BODY_2,
                color = JypColors.Text40,
        )
    }
}

@Composable
private fun ThemeList(
        selectedTheme: ThemeType?,
        onSelectTheme: (ThemeType) -> Unit,
) {
    LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ThemeType.values().forEach { themeType ->
            item {
                ThemeItem(
                        modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onSelectTheme.invoke(themeType) }
                        ),
                        themeType = themeType,
                        isSelected = themeType == selectedTheme
                )
            }
        }
    }
}

@Composable
private fun ThemeItem(
        modifier: Modifier = Modifier,
        themeType: ThemeType,
        isSelected: Boolean,
) {
    Box {
        Image(
                modifier = modifier
                        .align(Alignment.BottomCenter)
                        .width(96.dp)
                        .height(140.dp)
                        .let {
                            if (themeType == ThemeType.DEFAULT) {
                                it.border(
                                        width = 1.dp,
                                        color = JypColors.Text20,
                                        shape = RoundedCornerShape(6.dp)
                                )
                            } else {
                                it
                            }
                        },
                painter = painterResource(id = themeType.imageRes),
                contentDescription = null,
        )
        JypText(
                modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 12.dp, bottom = 10.dp),
                text = themeType.description,
                type = TextType.TITLE_6,
                color = if (themeType == ThemeType.DEFAULT) {
                    JypColors.Text75
                } else {
                    JypColors.Text_white
                },
        )

        if (isSelected) {
            Image(
                    modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(
                                    top = 2.dp,
                                    end = 4.dp,
                            ),
                    painter = JypPainter.check,
                    contentDescription = null,
            )
        }
    }
}

@Composable
private fun Footer(
        selectedTheme: ThemeType?,
        onSubmitTheme: (ThemeType) -> Unit,
) {
    JypTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "선택 완료하기",
            buttonType = ButtonType.THICK,
            buttonColorSet = ButtonColorSetType.PINK,
            enabled = selectedTheme != null,
            onClickEnabled = {
                selectedTheme?.let(onSubmitTheme)
            },
    )
}
