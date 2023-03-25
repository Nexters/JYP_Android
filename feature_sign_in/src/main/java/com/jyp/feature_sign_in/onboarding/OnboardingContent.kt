package com.jyp.feature_sign_in.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingContent(
    onBackPressed: () -> Unit,
    onOnboardingFinished: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val onboardings = listOf(
        OnboardingEnum.ONBOARDING_01,
        OnboardingEnum.ONBOARDING_02
    )

    when (pagerState.currentPage) {
        0 -> BackHandler(enabled = true) { onBackPressed() }
        1 -> BackHandler(enabled = true) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white200)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingViewPager(
                pagerState = pagerState,
                onboardings = onboardings,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
            )
            JypTextButton(
                text = stringResource(id = com.jyp.jyp_design.R.string.button_next),
                buttonType = ButtonType.THICK,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(
                        top = 36.dp,
                        bottom = 28.dp
                    ),
                enabled = true,
                buttonColorSet = ButtonColorSetType.PINK,
                onClickEnabled = {
                    when (pagerState.currentPage >= onboardings.size - 1) {
                        true -> onOnboardingFinished()
                        false -> coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            )
        }
        Image(
            painter = painterResource(
                id = com.jyp.jyp_design.R.drawable.image_logotype
            ),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .wrapContentHeight()
                .padding(
                    start = 24.dp,
                    top = 24.dp
                )
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun OnboardingViewPager(
    pagerState: PagerState,
    onboardings: List<OnboardingEnum>,
    modifier: Modifier
) {
    HorizontalPager(
        count = onboardings.size,
        modifier = modifier,
        state = pagerState,
        reverseLayout = false,
        itemSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) { page ->
        OnboardingScreenItem(onboarding = onboardings[page])
    }
}

@Composable
internal fun OnboardingScreenItem(
    onboarding: OnboardingEnum
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = JypColors.Background_white100,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
    ) {
        JypText(
            text = stringResource(id = onboarding.titleRes),
            type = TextType.TITLE_1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
                .padding(top = 88.dp),
            maxLines = 2,
            textAlign = TextAlign.Start,
            color = JypColors.Text90
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .shadow(
                        elevation = 32.dp,
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 40.dp,
                            bottomEnd = 40.dp
                        ),
                        spotColor = JypColors.Border_grey
                    )
                    .background(
                        color = JypColors.Background_white100,
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 40.dp,
                            bottomEnd = 40.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = JypColors.Background_white100)
            )
            Image(
                painter = painterResource(id = onboarding.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 40.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
internal fun OnboardingContentPreview() {
    OnboardingContent(
        onBackPressed = {},
        onOnboardingFinished = {}
    )
}