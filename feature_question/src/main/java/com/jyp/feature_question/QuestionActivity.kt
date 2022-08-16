package com.jyp.feature_question

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class QuestionActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels {
        QuestionViewModelFactory(QuestionRepositoryImpl)
    }

    @OptIn(ExperimentalPagerApi::class)
    private lateinit var pagerState: PagerState
    private lateinit var coroutineScope: CoroutineScope

    @OptIn(ExperimentalPagerApi::class)
    private val isCurrentQuestionOptionSelected
        get() = viewModel.questionData.value?.get(pagerState.currentPage)?.selectedIndex != null

    @OptIn(ExperimentalPagerApi::class)
    private val isLastPage
        get() = pagerState.currentPage == viewModel.questionData.value?.lastIndex


    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            pagerState = rememberPagerState()
            coroutineScope = rememberCoroutineScope()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = JypColors.Background_white100
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
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

                    HorizontalPager(
                        count = viewModel.questionData.value?.size ?: 3,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f, false),
                        state = pagerState,
                        reverseLayout = false,
                        itemSpacing = 0.dp,
                        contentPadding = PaddingValues(0.dp)
                    ) { page ->
                        viewModel.questionData.value?.get(page)?.let {
                            QuestionScreen(questionData = it)
                        }
                    }
                }
                Column {
                    Spacer(modifier = Modifier.weight(1f))
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
            }
        }
    }
}