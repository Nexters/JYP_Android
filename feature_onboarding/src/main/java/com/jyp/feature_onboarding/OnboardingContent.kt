package com.jyp.feature_onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.jyp.jyp_design.ui.shadow.drawShadow
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingContent() {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val onboardings = listOf(
        OnboardingEnum.ONBOARDING_01,
        OnboardingEnum.ONBOARDING_02
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white200),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        OnboardingViewPager(
            pagerState = pagerState,
            onboardings = onboardings,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .weight(1f, false),
            itemModifier = Modifier
                .fillMaxSize()
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
                when (pagerState.currentPage == onboardings.size) {
                    true -> {} // Todo - Set intent to kakao sign in.
                    false -> coroutineScope.launch {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun OnboardingViewPager(
    pagerState: PagerState,
    onboardings: List<OnboardingEnum>,
    modifier: Modifier,
    itemModifier: Modifier
) {
    HorizontalPager(
        count = onboardings.size,
        modifier = modifier,
        state = pagerState,
        reverseLayout = false,
        itemSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) { page ->
        OnboardingScreenItem(
            onboarding = onboardings[page],
            modifier = itemModifier
        )
    }
}

@Composable
internal fun OnboardingScreenItem(
    onboarding: OnboardingEnum,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = com.jyp.jyp_design.R.drawable.image_logotype),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .wrapContentHeight()
                .align(Alignment.Start)
                .padding(
                    start = 24.dp,
                    top = 24.dp
                )
        )
        JypText(
            text = stringResource(id = onboarding.titleRes),
            type = TextType.TITLE_1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp),
            maxLines = 2,
            textAlign = TextAlign.Start,
            color = JypColors.Text90
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


@Composable
@Preview(showBackground = true)
internal fun OnboardingContentPreview() {
    OnboardingContent()
}