package com.jyp.feature_question.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jyp.feature_question.R
import com.jyp.feature_question.util.QuestionEnum
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionScreen(
    selectedQuestionOptions: List<Int?>,
    onClickQuestionOption: (index: Int, selectedOption: Int) -> Unit,
    onClickDoneQuestion: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val questions = listOf(
        QuestionEnum.QUESTION_01,
        QuestionEnum.QUESTION_02,
        QuestionEnum.QUESTION_03
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white100),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuestionAppBar(pagerState, coroutineScope)
        QuestionViewPager(
            viewPagerCount = questions.size,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, false),
            pagerState = pagerState
        ) { page ->

            QuestionScreenItem(
                question = questions[page],
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .weight(1f, false),

                questionOptionButtons = {
                    QuestionOptionButtons(
                        question = questions[page],
                        selectedQuestionOption = selectedQuestionOptions[pagerState.currentPage],
                        onClickQuestionOption = onClickQuestionOption
                    )
                }
            )
        }
        QuestionDoneButton(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            isLastQuestion = pagerState.currentPage == questions.size - 1,
            isOptionSelected = selectedQuestionOptions[pagerState.currentPage] != null,
            onClickDoneQuestion = onClickDoneQuestion
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionAppBar(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 12.dp)
            .padding(vertical = 12.dp)
    ) {
        IconButton(
            onClick = {
                if (pagerState.currentPage != 0) coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            enabled = pagerState.currentPage != 0
        ) {
            Icon(
                painter = painterResource(com.jyp.jyp_design.R.drawable.icon_left_arrow),
                contentDescription = null,
                tint = when (pagerState.currentPage == 0) {
                    true -> JypColors.Sub_black.copy(0f)
                    false -> JypColors.Sub_black.copy(1f)
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionViewPager(
    viewPagerCount: Int,
    modifier: Modifier,
    pagerState: PagerState,
    content: @Composable (page: Int) -> Unit
) {
    HorizontalPager(
        count = viewPagerCount,
        modifier = modifier,
        state = pagerState,
        reverseLayout = false,
        itemSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp),
        userScrollEnabled = false
    ) { page -> content(page) }
}

@Composable
internal fun QuestionScreenItem(
    question: QuestionEnum,
    modifier: Modifier,
    questionOptionButtons: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.icon_question),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
            text = stringResource(question.titleRes),
            type = TextType.TITLE_1,
            color = JypColors.Text90
        )
        Spacer(modifier = Modifier.size(88.dp))
        questionOptionButtons()
        Spacer(modifier = Modifier.size(44.dp))
    }
}

@Composable
internal fun QuestionOptionButtons(
    question: QuestionEnum,
    selectedQuestionOption: Int?,
    onClickQuestionOption: (index: Int, selectedOption: Int) -> Unit
) {
    stringArrayResource(id = question.optionsRes).forEachIndexed { index, optionString ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                    onClick = { onClickQuestionOption(question.index, index) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                .shadow(
                    elevation = 32.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = JypColors.Border_grey
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
                    color = when (selectedQuestionOption) {
                        index -> JypColors.Text80
                        null -> JypColors.Text80
                        else -> JypColors.Text80.copy(0.5f)
                    }
                )

                Image(
                    painter = JypPainter.check,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    alignment = Alignment.TopEnd,
                    alpha = when (selectedQuestionOption) {
                        index -> 1f
                        null -> 0f
                        else -> 0f
                    }
                )
            }
            Image(
                painter = painterResource(id = question.images[index]),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(
                        bottom = 12.dp,
                        end = 12.dp
                    ),
                alpha = when (selectedQuestionOption) {
                    index -> 1f
                    null -> 1f
                    else -> 0.5f
                }
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionDoneButton(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    isLastQuestion: Boolean,
    isOptionSelected: Boolean,
    onClickDoneQuestion: () -> Unit
) {
    JypTextButton(
        text = stringResource(
            id = when (isLastQuestion) {
                true -> R.string.question_done
                false -> R.string.question_next
            }
        ),
        buttonType = ButtonType.THICK,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 28.dp),
        enabled = isOptionSelected,
        buttonColorSet = when (isOptionSelected) {
            true -> ButtonColorSetType.PINK
            false -> ButtonColorSetType.GRAY
        },
        onClickEnabled = {
            when (isLastQuestion) {
                true -> onClickDoneQuestion()
                false -> coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        },
        onClickDisabled = {}
    )
}

@Composable
@Preview(showBackground = true)
internal fun QuestionScreenPreview() {
    QuestionScreen(
        selectedQuestionOptions = listOf<Int?>(0, 1, 0),
        onClickQuestionOption = { _, _ -> },
        onClickDoneQuestion = {}
    )
}
