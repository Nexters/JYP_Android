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
import com.google.accompanist.flowlayout.FlowRow
import com.google.android.material.internal.FlowLayout
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.tag.*
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

        val state = remember {
            mutableStateListOf(
                    Tag(type = TagType.Soso(), content = "모두 찬성"),
                    Tag(type = TagType.Soso(), content = "상관없어"),
                    Tag(type = TagType.Like(), content = "좋아1"),
                    Tag(type = TagType.Like(), content = "좋아2"),
                    Tag(type = TagType.Like(), content = "좋아3"),
                    Tag(type = TagType.Like(), content = "좋아4"),
                    Tag(type = TagType.Like(), content = "좋아5"),
                    Tag(type = TagType.Like(), content = "좋아6"),
                    Tag(type = TagType.Like(), content = "좋아7"),
                    Tag(type = TagType.Dislike(), content = "싫어1"),
                    Tag(type = TagType.Dislike(), content = "싫어2"),
                    Tag(type = TagType.Dislike(), content = "싫어3"),
                    Tag(type = TagType.Dislike(), content = "싫어4"),
            )
        }

        when (step) {
            CreatePlannerStep.TITLE -> CreatePlannerTitleArea()
            CreatePlannerStep.DATE -> CreatePlannerDateArea()
            CreatePlannerStep.TASTE -> CreatePlannerTasteArea(
                    tags = state,
                    // TODO : 아래 로직은 ViewModel로 이동해야한다
                    tagClick = { tag ->
                        val clickIndex = state.indexOf(tag)
                        val tagState = tag.state
                        val newTag = tag.copy(
                                state = when (tagState) {
                                    TagState.DEFAULT -> TagState.SELECTED
                                    TagState.SELECTED -> TagState.DEFAULT
                                    TagState.DISABLED -> TagState.DISABLED
                                }
                        )

                        state[clickIndex] = newTag

                        val clickedTagCount = state.count { it.state == TagState.SELECTED }
                        if (clickedTagCount >= 3) {
                            repeat(state.size) { i ->
                                if (state[i].state == TagState.DEFAULT) {
                                    state[i] = state[i].copy(state = TagState.DISABLED)
                                }
                            }
                        } else {
                            repeat(state.size) { i ->
                                if (state[i].state == TagState.DISABLED) {
                                    state[i] = state[i].copy(state = TagState.DEFAULT)
                                }
                            }
                        }
                    }
            )
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
private fun CreatePlannerTasteArea(
        tags: List<Tag>,
        tagClick: (Tag) -> Unit,
) {
    val sosoTags = tags.filter { tag -> tag.type is TagType.Soso }
    val likeTags = tags.filter { tag -> tag.type is TagType.Like }
    val dislikeTags = tags.filter { tag -> tag.type is TagType.Dislike }

    TastesSection(
            tagCategory = "상관 없어요 태그",
            tags = sosoTags,
            tagClick = tagClick,
    )
    Spacer(modifier = Modifier.size(40.dp))
    TastesSection(
            tagCategory = "좋아요 태그",
            tags = likeTags,
            tagClick = tagClick,
    )
    Spacer(modifier = Modifier.size(40.dp))
    TastesSection(
            tagCategory = "싫어요 태그",
            tags = dislikeTags,
            tagClick = tagClick,
    )
}

@Composable
private fun TastesSection(
        tagCategory: String,
        tags: List<Tag>,
        tagClick: (Tag) -> Unit,
) {
    Column {
        JypText(
                text = tagCategory,
                type = TextType.TITLE_6,
                color = JypColors.Text90,
        )
        Spacer(modifier = Modifier.size(16.dp))
        FlowRow {
            FlowRow(
                    crossAxisSpacing = 12.dp,
                    mainAxisSpacing = 8.dp,
            ) {
                tags.forEach { tag ->
                    DecoratedTag(
                            modifier = Modifier
                                    .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = { tagClick.invoke(tag) },
                                    ),
                            tagType = tag.type,
                            tagState = tag.state,
                            content = tag.content,
                    )
                }
            }
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
