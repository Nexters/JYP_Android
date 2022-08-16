package com.jyp.feature_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun QuestionScreen(
    questionData: QuestionData
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.icon_question),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
            text = stringResource(questionData.titleRes),
            type = TextType.TITLE_1,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(88.dp))
        QuestionOptionRadioButtons(questionData = questionData)
        Spacer(modifier = Modifier.size(44.dp))
    }
}

@Composable
fun QuestionOptionRadioButtons(
    questionData: QuestionData
) {
    stringArrayResource(id = questionData.optionsRes).forEachIndexed { index, optionString ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = false,
                    enabled = true,
                    role = Role.RadioButton,
                    onClick = { /*onClickEnabled*/ }
                )
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 12.dp
                    ),
                verticalAlignment = Alignment.Top
            ) {
                JypText(
                    text = optionString,
                    type = TextType.BODY_1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true),
                    color = when (
                        questionData.selectedIndex == null ||
                                questionData.selectedIndex == index
                    ) {
                        true -> JypColors.Text80
                        false -> JypColors.Text80.copy(0.5f)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_check),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    alignment = Alignment.TopEnd,
                    alpha = when (questionData.selectedIndex == index) {
                        true -> 1f
                        false -> 0f
                    }
                )
            }
            Image(
                painter = painterResource(id = questionData.images[index]),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(
                        bottom = 12.dp,
                        end = 12.dp
                    ),
                alpha = when (
                    questionData.selectedIndex == null ||
                            questionData.selectedIndex == index
                ) {
                    true -> 1f
                    false -> 0.5f
                }
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
@Preview(showBackground = true)
internal fun QuestionScreenPreview() {
    QuestionScreen(
        questionData = QuestionData(
            id = 0,
            titleRes = R.string.question01,
            optionsRes = R.array.question01_options,
            images = listOf(
                R.drawable.illust_question01_01,
                R.drawable.illust_question01_02
            ),
            0
        )
    )
}