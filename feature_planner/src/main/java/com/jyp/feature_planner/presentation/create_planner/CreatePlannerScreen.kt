package com.jyp.feature_planner.presentation.create_planner

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerSubmit
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.tag.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CreatePlannerScreen(
    step: CreatePlannerStep,
    selectDateClick: () -> Unit,
    startDateMillis: Long,
    endDateMillis: Long,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    addTagClick: (TagType) -> Unit,
    submit: (CreatePlannerSubmit) -> Unit
) {
    var title by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

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
            CreatePlannerContent(
                modifier = Modifier.weight(1f),
                step = step,
                title = title,
                titleChange = { title = it },
                selectDateClick = selectDateClick,
                startDateMillis = startDateMillis,
                endDateMillis = endDateMillis,
                tags = tags,
                tagClick = tagClick,
                addTagClick = addTagClick
            )

            JypTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                text = if (step == CreatePlannerStep.TASTE) {
                    "여행 계획 시작하기"
                } else {
                    "다음으로"
                },
                buttonType = ButtonType.THICK,
                buttonColorSet = ButtonColorSetType.PINK,
                enabled = when (step) {
                    CreatePlannerStep.TITLE -> title.isNotEmpty() && title.length <= 6
                    CreatePlannerStep.DATE -> startDateMillis > 0 && endDateMillis > 0
                    CreatePlannerStep.TASTE -> tags.count { it.state == TagState.SELECTED } >= 1
                },
                onClickEnabled = {
                    keyboardController?.hide()
                    when (step) {
                        CreatePlannerStep.TITLE -> submit.invoke(
                            CreatePlannerSubmit.Title(title)
                        )
                        CreatePlannerStep.DATE -> submit.invoke(
                            CreatePlannerSubmit.Date(startDateMillis, endDateMillis)
                        )
                        CreatePlannerStep.TASTE -> submit.invoke(
                            CreatePlannerSubmit.Taste(tags.filter {
                                it.state == TagState.SELECTED
                            })
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun CreatePlannerHeader(
    step: CreatePlannerStep
) {
    Column {
        JypText(
            text = stringResource(id = step.titleRes),
            type = TextType.TITLE_1,
            color = JypColors.Text90
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
            text = stringResource(id = step.descriptionRes),
            type = TextType.BODY_2,
            color = JypColors.Text40
        )
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun CreatePlannerContent(
    modifier: Modifier = Modifier,
    step: CreatePlannerStep,
    title: String,
    titleChange: (String) -> Unit,
    selectDateClick: () -> Unit,
    startDateMillis: Long,
    endDateMillis: Long,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    addTagClick: (TagType) -> Unit
) {
    when (step) {
        CreatePlannerStep.TITLE -> CreatePlannerTitleArea(
            modifier = modifier,
            title = title,
            titleChange = titleChange
        )
        CreatePlannerStep.DATE -> CreatePlannerDateArea(
            modifier = modifier,
            selectDateClick = selectDateClick,
            startDateMillis = startDateMillis,
            endDateMillis = endDateMillis
        )
        CreatePlannerStep.TASTE -> CreatePlannerTasteArea(
            modifier = modifier,
            tags = tags,
            tagClick = tagClick,
            addTagClick = addTagClick
        )
    }
}

@Composable
private fun CreatePlannerTitleArea(
    modifier: Modifier = Modifier,
    title: String,
    titleChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier) {
        JypTextInput(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            type = TextInputType.FIELD,
            text = title,
            valueChange = titleChange,
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
                "여수"
            ).forEach {
                PlannerCreateTitleSuggestion(
                    suggestionTitle = it,
                    clickSuggestion = titleChange
                )
            }
        }
        if (title.length > 6) {
            Spacer(modifier = Modifier.weight(1f))
            JypText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "입력 가능 글자를 초과했어요",
                type = TextType.BODY_3,
                color = JypColors.Pink
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun PlannerCreateTitleSuggestion(
    suggestionTitle: String,
    clickSuggestion: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(JypColors.Tag_white_blue100)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { clickSuggestion.invoke(suggestionTitle) }
            ),
        contentAlignment = Alignment.Center
    ) {
        JypText(
            text = suggestionTitle,
            type = TextType.TAG_1,
            color = JypColors.Sub_blue300
        )
    }
}

@Composable
private fun CreatePlannerDateArea(
    modifier: Modifier,
    selectDateClick: () -> Unit,
    startDateMillis: Long,
    endDateMillis: Long
) {
    Box(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = selectDateClick
        )
    ) {
        Row {
            DateForm(
                modifier = Modifier.weight(1f),
                title = "여행 시작",
                millis = startDateMillis
            )
            Spacer(modifier = Modifier.size(8.dp))
            DateFormSeparator()
            Spacer(modifier = Modifier.size(8.dp))
            DateForm(
                modifier = Modifier.weight(1f),
                title = "여행 종료",
                millis = endDateMillis
            )
        }
    }
}

@Composable
private fun DateForm(
    modifier: Modifier = Modifier,
    title: String,
    millis: Long
) {
    Column(modifier = modifier) {
        JypText(
            text = title,
            type = TextType.BODY_4,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(JypColors.Background_white200)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            val date = if (millis <= 0) {
                " "
            } else {
                SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Date(millis))
            }

            JypText(
                text = date,
                type = TextType.TITLE_2,
                color = JypColors.Text90
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
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            JypText(
                text = "-",
                type = TextType.TITLE_2,
                color = JypColors.Text90
            )
        }
    }
}

@Composable
private fun CreatePlannerTasteArea(
    modifier: Modifier = Modifier,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    addTagClick: (TagType) -> Unit
) {
    val sosoTags = tags.filter { tag -> tag.type is TagType.Soso }
    val likeTags = tags.filter { tag -> tag.type is TagType.Like }
    val dislikeTags = tags.filter { tag -> tag.type is TagType.Dislike }

    Column(
        modifier = modifier
    ) {
        TastesSection(
            tagCategory = "상관 없어요 태그",
            tags = sosoTags,
            tagClick = tagClick
        )
        Spacer(modifier = Modifier.size(40.dp))
        TastesSection(
            tagCategory = "좋아요 태그",
            tags = likeTags,
            tagClick = tagClick,
            addTagClick = { addTagClick.invoke(TagType.Like) }
        )
        Spacer(modifier = Modifier.size(40.dp))
        TastesSection(
            tagCategory = "싫어요 태그",
            tags = dislikeTags,
            tagClick = tagClick,
            addTagClick = { addTagClick.invoke(TagType.Dislike) }
        )
    }
}

@Composable
private fun TastesSection(
    tagCategory: String,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    addTagClick: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            JypText(
                text = tagCategory,
                type = TextType.TITLE_6,
                color = JypColors.Text90
            )

            addTagClick?.let {
                Image(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = addTagClick
                    ),
                    painter = JypPainter.add,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        FlowRow {
            FlowRow(
                crossAxisSpacing = 12.dp,
                mainAxisSpacing = 8.dp
            ) {
                tags.forEach { tag ->
                    DecoratedTag(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { tagClick.invoke(tag) }
                        ),
                        tagType = tag.type,
                        tagState = tag.state,
                        content = tag.content,
                        tagCount = 0
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun CreatePlannerScreenTitlePreview() {
    CreatePlannerScreen(
        step = CreatePlannerStep.TITLE,
        selectDateClick = {},
        startDateMillis = 0,
        endDateMillis = 0,
        tags = emptyList(),
        tagClick = {},
        addTagClick = {},
        submit = {}
    )
}

@Composable
@Preview(showBackground = true)
internal fun CreatePlannerScreenDatePreview() {
    CreatePlannerScreen(
        step = CreatePlannerStep.DATE,
        selectDateClick = {},
        startDateMillis = 0,
        endDateMillis = 0,
        tags = emptyList(),
        tagClick = {},
        addTagClick = {},
        submit = {}
    )
}

@Composable
@Preview(showBackground = true)
internal fun CreatePlannerScreenTastePreview() {
    CreatePlannerScreen(
        step = CreatePlannerStep.TASTE,
        selectDateClick = {},
        startDateMillis = 0,
        endDateMillis = 0,
        tags = emptyList(),
        tagClick = {},
        addTagClick = {},
        submit = {}
    )
}