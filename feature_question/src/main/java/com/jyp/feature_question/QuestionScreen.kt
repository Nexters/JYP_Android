package com.jyp.feature_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionScreen(
    selectedQuestionOptions: List<Int?>,
    onQuestionOptionSelected: (index: Int, selectedOption: Int) -> Unit,
    onQuestionFinished: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState().apply {
        coroutineScope.launch { disableScrolling() }
    }
    val questions = listOf(
        QuestionEnum.QUESTION_01,
        QuestionEnum.QUESTION_02,
        QuestionEnum.QUESTION_03
    )
    val isLastQuestion by remember {
        mutableStateOf(pagerState.currentPage == questions.size - 1)
    }
    val isOptionSelected by remember {
        mutableStateOf(
            selectedQuestionOptions.isNotEmpty() &&
                    selectedQuestionOptions[pagerState.currentPage] != null
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white100),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuestionAppBar(pagerState, coroutineScope)
        QuestionViewPager(
            pagerState = pagerState,
            questions = questions,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, false)
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
                        isOptionSelected = isOptionSelected,
                        onQuestionOptionSelected = onQuestionOptionSelected
                    )
                }
            )
        }
        QuestionDoneButton(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            isLastQuestion = isLastQuestion,
            isOptionSelected = isOptionSelected,
            onQuestionFinished = onQuestionFinished
        )
    }
}

@ExperimentalPagerApi
internal suspend fun PagerState.disableScrolling() {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        awaitCancellation()
    }
}

@ExperimentalPagerApi
internal suspend fun PagerState.enableScrolling() {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        // Do nothing, just cancel the previous indefinite "scroll"
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
                    pagerState.enableScrolling()
                    pagerState.scrollToPage(pagerState.currentPage - 1)
                    pagerState.disableScrolling()
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
    pagerState: PagerState,
    questions: List<QuestionEnum>,
    modifier: Modifier,
    content: @Composable (page: Int) -> Unit
) {
    HorizontalPager(
        count = questions.size,
        modifier = modifier,
        state = pagerState,
        reverseLayout = false,
        itemSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
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
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(88.dp))
        questionOptionButtons()
        Spacer(modifier = Modifier.size(44.dp))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionOptionButtons(
    question: QuestionEnum,
    isOptionSelected: Boolean,
    onQuestionOptionSelected: (index: Int, selectedOption: Int) -> Unit
) {
    stringArrayResource(id = question.optionsRes).forEachIndexed { index, optionString ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = isOptionSelected,
                    enabled = true,
                    role = Role.RadioButton,
                    onClick = {
                        onQuestionOptionSelected(question.index, index)
                    }
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
                    color = when (isOptionSelected) {
                        true -> JypColors.Text80
                        false -> JypColors.Text80.copy(0.5f)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_check),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    alignment = Alignment.TopEnd,
                    alpha = when (isOptionSelected) {
                        true -> 1f
                        false -> 0f
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
                alpha = when (isOptionSelected) {
                    true -> 1f
                    false -> 0.5f
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
    onQuestionFinished: () -> Unit
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
                true -> onQuestionFinished()
                false -> coroutineScope.launch {
                    pagerState.enableScrolling()
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                    pagerState.disableScrolling()
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
        onQuestionOptionSelected = { _, _ -> },
        onQuestionFinished = {}
    )
}
