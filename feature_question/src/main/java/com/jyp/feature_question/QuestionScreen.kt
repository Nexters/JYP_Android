package com.jyp.feature_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionScreen() {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val questions = listOf(
        QuestionEnum.QUESTION_01,
        QuestionEnum.QUESTION_02,
        QuestionEnum.QUESTION_03
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = JypColors.Background_white100
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            QuestionAppBar(pagerState, coroutineScope)
            QuestionViewPager(
                pagerState = pagerState,
                questions = questions,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, false)
            )
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            QuestionDoneButton(
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                questions = questions,
                viewModel = viewModel
            )
        }
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
                    pagerState.scrollToPage(pagerState.currentPage - 1)
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
    modifier: Modifier
) {
    HorizontalPager(
        count = questions.size,
        modifier = modifier,
        state = pagerState,
        reverseLayout = false,
        itemSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) { page ->
        QuestionScreenItem(question = questions[page])
    }
}

@Composable
internal fun QuestionScreenItem(
    question: QuestionEnum
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
            text = stringResource(question.titleRes),
            type = TextType.TITLE_1,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(88.dp))
        QuestionOptionRadioButtons(
            question = question,
            viewModel = QuestionViewModel()
        )
        Spacer(modifier = Modifier.size(44.dp))
    }
}

@Composable
internal fun QuestionOptionRadioButtons(
    question: QuestionEnum,
    viewModel: QuestionViewModel
) {
    stringArrayResource(id = question.optionsRes).forEachIndexed { index, optionString ->
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
                        viewModel.selectedQuestionOptions.value?.get(index) == null ||
                                question.index == index
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
                    alpha = when (viewModel.selectedQuestionOptions.value?.get(index) == index) {
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
                alpha = when (viewModel.selectedQuestionOptions.value?.get(index) == index) {
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
    questions: List<QuestionEnum>,
    viewModel: QuestionViewModel
) {
    val isLastPage = pagerState.currentPage == questions.size
    val isCurrentQuestionOptionSelected =
        viewModel.selectedQuestionOptions.value?.get(pagerState.currentPage) != null


    JypTextButton(
        text = stringResource(
            id = when (isLastPage) {
                true -> R.string.question_done
                false -> R.string.question_next
            }
        ),
        buttonType = ButtonType.THICK,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 28.dp),
        enabled = isCurrentQuestionOptionSelected,
        buttonColorSet = when (isCurrentQuestionOptionSelected) {
            true -> ButtonColorSetType.PINK
            false -> ButtonColorSetType.GRAY
        },
        onClickEnabled = {
            when (isLastPage) {
                true -> {} // Todo - Set intent to Main.
                false -> coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            }
        },
        onClickDisabled = {}
    )
}

@Composable
@Preview(showBackground = true)
internal fun QuestionScreenPreview() {
    QuestionScreen()
}