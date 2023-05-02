package com.jyp.feature_planner.presentation.create_planner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.tag.TagType
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun AddTagBottomSheetScreen(
    modifier: Modifier = Modifier,
    tagType: TagType,
    onClickCancel: () -> Unit,
    onClickSubmit: (PlannerTag) -> Unit,
) {
    var tagName by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(JypColors.Background_white100)
            .padding(horizontal = 24.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.size(28.dp))
            Header(
                title = when (tagType) {
                    is TagType.Dislike -> "싫어요 태그 생성"
                    is TagType.Like -> "좋아요 태그 생성"
                    is TagType.Soso -> ""
                },
                onClickCancel = {
                    onClickCancel.invoke()

                    tagName = ""
                },
            )
            Spacer(modifier = Modifier.size(30.dp))
            Content(
                tagName = tagName,
                onTagNameChanged = { tagName = it },
            )
        }
        JypTextButton(
            modifier = Modifier
                .padding(bottom = 28.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            text = "추가하기",
            buttonType = ButtonType.THICK,
            buttonColorSet = ButtonColorSetType.PINK,
            enabled = tagName.isNotEmpty() && tagName.length <= 6,
            onClickEnabled = {
                onClickSubmit.invoke(PlannerTag(tagType, tagName))

                tagName = ""
            },
        )
    }
}

@Composable
private fun Header(
    title: String,
    onClickCancel: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        JypText(
            text = title,
            type = TextType.TITLE_3,
            color = JypColors.Text90,
        )

        JypText(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClickCancel,
            ),
            text = "취소",
            type = TextType.TITLE_4,
            color = JypColors.Text40,
        )
    }
}

@Composable
private fun Content(
    tagName: String,
    onTagNameChanged: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            JypText(
                text = "태그 이름",
                type = TextType.BODY_3,
                color = JypColors.Text75,
            )

            JypText(
                text = "6글자 이하 가능",
                type = TextType.BODY_4,
                color = JypColors.Text75,
            )
        }

        JypTextInput(
            modifier = Modifier
                .fillMaxWidth(),
            text = tagName,
            valueChange = onTagNameChanged,
            type = TextInputType.FIELD,
            hint = "입력해주세요"
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AddTagBottomSheetScreenPreview() {
    AddTagBottomSheetScreen(
        tagType = TagType.Like,
        onClickCancel = {},
        onClickSubmit = {},
    )
}
