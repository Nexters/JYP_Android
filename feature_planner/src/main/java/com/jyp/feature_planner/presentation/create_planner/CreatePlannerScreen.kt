package com.jyp.feature_planner.presentation.create_planner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun CreatePlannerScreen(
        step: CreatePlannerStep,
) {
    Box(
            modifier = Modifier
                    .background(JypColors.Background_white100)
                    .padding(horizontal = 24.dp)
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
        ) {
            CreatePlannerHeader(step = step)
        }

        JypTextButton(
                modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 28.dp),
                text = "다음으로",
                buttonType = ButtonType.THICK,
                buttonColorSet = ButtonColorSetType.PINK,
        )
    }

}

@Composable
private fun CreatePlannerHeader(
        step: CreatePlannerStep,
) {
    Column {
        JypText(
                text = stringResource(id = step.titleRes),
                type = TextType.TITLE_1,
                color = JypColors.Text90,
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
                text = stringResource(id = step.descriptionRes),
                type = TextType.BODY_2,
                color = JypColors.Text40,
        )
        Spacer(modifier = Modifier.size(48.dp))

        when (step) {
            CreatePlannerStep.TITLE -> CreatePlannerTitleArea()
            CreatePlannerStep.DATE -> CreatePlannerDateArea()
            CreatePlannerStep.TASTE -> Unit
        }
    }
}

@Composable
private fun CreatePlannerTitleArea() {
    var inputText by remember {
        mutableStateOf("")
    }

    Column {
        JypTextInput(
                modifier = Modifier
                        .fillMaxWidth(),
                type = TextInputType.FIELD,
                text = inputText,
                valueChange = { inputText = it },
                hint = "예) 제주도 여행기"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                    "제주도",
                    "강릉",
                    "부산",
                    "경주",
                    "여수",
            ).forEach {
                PlannerCreateTitleSuggestion(
                        suggestionTitle = it,
                        clickSuggestion = { clickedText -> inputText = clickedText }
                )
            }
        }
    }
}

@Composable
private fun PlannerCreateTitleSuggestion(
        suggestionTitle: String,
        clickSuggestion: (String) -> Unit,
) {
    Box(
            modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(JypColors.Tag_white_blue100)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { clickSuggestion.invoke(suggestionTitle) }
                    ),
            contentAlignment = Alignment.Center,
    ) {
        JypText(
                text = suggestionTitle,
                type = TextType.TAG_1,
                color = JypColors.Sub_blue300,
        )
    }
}

@Composable
private fun CreatePlannerDateArea() {
    Row {
        DateForm(
                modifier = Modifier.weight(1f),
                title = "여행 시작",
                date = "22.07.17",
        )
        Spacer(modifier = Modifier.size(8.dp))
        DateFormSeparator()
        Spacer(modifier = Modifier.size(8.dp))
        DateForm(
                modifier = Modifier.weight(1f),
                title = "여행 종료",
                date = "22.07.19",
        )
    }
}

@Composable
private fun DateForm(
        modifier: Modifier = Modifier,
        title: String,
        date: String,
) {
    Column(modifier = modifier) {
        JypText(
                text = title,
                type = TextType.BODY_4,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
                modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .background(JypColors.Background_white200)
                        .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
        ) {
            JypText(
                    text = date,
                    type = TextType.TITLE_2,
                    color = JypColors.Text90,
            )
        }
    }
}

@Composable
private fun DateFormSeparator() {
    Column {
        JypText(
                text = " ",
                type = TextType.BODY_4,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
                modifier = Modifier.padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
        ) {
            JypText(
                    text = "-",
                    type = TextType.TITLE_2,
                    color = JypColors.Text90,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun CreatePlannerScreenPreview() {
    CreatePlannerScreen(
            CreatePlannerStep.TITLE,
    )
}

@Composable
@Preview(showBackground = true)
private fun TitleAreaPreview() {
    CreatePlannerTitleArea()
}

@Composable
@Preview
@Preview(showBackground = true)
private fun DateFormAreaPreview() {
    CreatePlannerDateArea()
}
