package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_my_journey.R
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.AvatarList
import com.jyp.jyp_design.ui.shadow.drawShadow
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun MyJourneyScreen(
        journeyPropensity: String,
        userName: String,
        plannedJourneys: List<Journey>,
        pastJourneys: List<Journey>,
        onClickNewJourney: () -> Unit,
        onClickPlanner: () -> Unit,
) {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .background(JypColors.Background_white200),
    ) {
        MyJourneyHeader(
                journeyPropensity = journeyPropensity,
                userName = userName,
        )

        MyJourneyContent(
                plannedJourneys = plannedJourneys,
                pastJourneys = pastJourneys,
                onClickNewJourney = onClickNewJourney,
                onClickPlanner = onClickPlanner,
        )
    }
}

@Composable
internal fun MyJourneyHeader(
        journeyPropensity: String,
        userName: String,
) {
    Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 35.dp),
            verticalAlignment = Alignment.Bottom
    ) {
        Text(
                modifier = Modifier.padding(start = 24.dp),
                text = stringResource(
                        id = R.string.my_journey_header,
                        formatArgs = arrayOf(journeyPropensity, userName),
                ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = JypColors.Text90,
        )
        Image(
                modifier = Modifier.padding(start = 6.dp),
                painter = painterResource(id = R.drawable.ic_carrier_blue),
                contentDescription = null,
        )
    }
}

@Composable
internal fun MyJourneyContent(
        plannedJourneys: List<Journey>,
        pastJourneys: List<Journey>,
        onClickNewJourney: () -> Unit,
        onClickPlanner: () -> Unit,
) {
    var selectedTabPosition by remember {
        mutableStateOf(0)
    }

    Column(
            modifier = Modifier
                    .background(JypColors.Background_white100),
    ) {
        MyJourneyContentTab(
                tabTitles = listOf(
                        stringResource(id = R.string.my_journey_planned_journey),
                        stringResource(id = R.string.my_journey_past_journey),
                ),
                selectedTabPosition = selectedTabPosition,
                tabSelected = { selectedTabPosition = it },
        )

        when (selectedTabPosition) {
            0 -> PlannedJourney(
                    journeys = plannedJourneys,
                    onClickNewJourney = onClickNewJourney,
                    onClickPlanner = onClickPlanner,
            )
            1 -> PastJourney(
                    journeys = pastJourneys,
                    onClickPlanner = onClickPlanner,
            )
        }

    }
}

@Composable
internal fun MyJourneyContentTab(
        tabTitles: List<String>,
        selectedTabPosition: Int,
        tabSelected: (position: Int) -> Unit,
) {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                                color = JypColors.Background_white200,
                                start = Offset(0f, size.height + 1.5.dp.toPx()),
                                end = Offset(size.width, size.height + 1.5.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                        )
                    }
                    .padding(top = 24.dp, start = 24.dp, end = 20.dp)
    ) {
        tabTitles.forEachIndexed { index, tabTitle ->
            val selected = selectedTabPosition == index
            Text(
                    modifier = Modifier
                            .let { modifier ->
                                if (index > 0) {
                                    modifier.padding(start = 28.dp)
                                } else {
                                    modifier
                                }
                            }
                            .drawWithContent {
                                drawContent()
                                if (selected) {
                                    drawRoundRect(
                                            color = JypColors.Text80,
                                            topLeft = Offset(0f, size.height + 3.dp.toPx()),
                                            cornerRadius = CornerRadius(x = 16.dp.toPx(), y = 16.dp.toPx()),
                                    )
                                }
                            }
                            .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple()
                            ) {
                                tabSelected.invoke(index)
                            }
                            .padding(vertical = 6.dp),
                    text = tabTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selected) {
                        JypColors.Text80
                    } else {
                        JypColors.Text40
                    },
            )
        }
    }
}

@Composable
internal fun PlannedJourney(
        journeys: List<Journey>,
        onClickNewJourney: () -> Unit,
        onClickPlanner: () -> Unit,
) {
    if (journeys.isEmpty()) {
        PlannedJourneyEmptyScreen(onClickNewJourney = onClickNewJourney)
    } else {
        LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            journeys.forEachIndexed { index, journey ->
                item {
                    JourneyItem(
                            journeyName = journey.title,
                            dDay = journey.dDay,
                            startDay = journey.startDay,
                            endDay = journey.endDay,
                            onClickPlanner = onClickPlanner,
                            themeType = ThemeType.values()[journey.theme],
                            profileUrls = journey.profileUrls,
                    )
                }
            }
        }
    }
}

@Composable
internal fun PlannedJourneyEmptyScreen(
        onClickNewJourney: () -> Unit,
) {
    Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Column(
                modifier = Modifier
                        .padding(vertical = 24.dp)
                        .width(width = 276.dp)
                        .fillMaxHeight()
                        .drawShadow(
                                color = JypColors.Border_grey,
                                borderRadius = 16.dp,
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(
                                color = JypColors.Background_white100,
                                shape = RoundedCornerShape(16.dp),
                        )
                        .padding(20.dp),
        ) {
            Column(
                    modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
            ) {
                Image(
                        painter = painterResource(id = R.drawable.ic_planned_journey_empty),
                        contentDescription = null,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                        text = stringResource(id = R.string.my_journey_empty_description_planned_journey),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = JypColors.Text75,
                        textAlign = TextAlign.Center
                )
            }

            Button(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                    onClick = onClickNewJourney,
            ) {
                Text(text = "만들기", fontSize = 16.sp, color = JypColors.Text_white)
            }
        }
    }
}

@Composable
internal fun PastJourney(
        journeys: List<Journey>,
        onClickPlanner: () -> Unit,
) {
    if (journeys.isEmpty()) {
        PastJourneyEmptyScreen()
    } else {
        LazyRow(
                contentPadding = PaddingValues(start = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            journeys.forEach { journey ->
                item {
                    JourneyItem(
                            journeyName = journey.title,
                            dDay = journey.dDay,
                            startDay = journey.startDay,
                            endDay = journey.endDay,
                            onClickPlanner = onClickPlanner,
                            themeType = ThemeType.values()[journey.theme],
                            profileUrls = journey.profileUrls,
                    )
                }
            }
        }
    }
}

@Composable
internal fun PastJourneyEmptyScreen() {
    Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Column(
                modifier = Modifier
                        .padding(vertical = 24.dp)
                        .width(width = 276.dp)
                        .fillMaxHeight()
                        .drawShadow(
                                color = JypColors.Border_grey,
                                borderRadius = 16.dp,
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(
                                color = JypColors.Background_white100,
                                shape = RoundedCornerShape(16.dp),
                        )
                        .padding(20.dp),
        ) {
            Column(
                    modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
            ) {
                Image(
                        painter = painterResource(id = R.drawable.ic_past_journey_empty),
                        contentDescription = null,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                        text = stringResource(id = R.string.my_journey_empty_description_past_journey),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = JypColors.Text75,
                        textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
internal fun JourneyItem(
        onClickPlanner: () -> Unit,
        journeyName: String,
        dDay: String,
        startDay: String,
        endDay: String,
        themeType: ThemeType,
        profileUrls: List<String>,
) {
    Box(
            modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .width(276.dp)
                    .fillMaxHeight()
                    .padding(vertical = 24.dp)
                    .background(
                            color = themeType.backgroundColor,
                            shape = RoundedCornerShape(16.dp)),
    ) {
        Image(
                modifier = Modifier.align(Alignment.BottomCenter),
                painter = painterResource(id = themeType.imageRes),
                contentDescription = null,
        )

        AvatarList(
                modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp),
                profileImageUrls = profileUrls,
                width = 44.dp,
                height = 44.dp,
                borderColor = themeType.profileBorderColor,
                limitListCount = 4
        )

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onClickPlanner
                        ),
        ) {
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                JypText(
                        modifier = Modifier
                                .background(
                                        color = JypColors.Sub_black,
                                        shape = RoundedCornerShape(6.dp)
                                )
                                .padding(
                                        vertical = 4.dp,
                                        horizontal = 9.dp,
                                ),
                        text = dDay,
                        type = TextType.TAG_2,
                        color = JypColors.Text_white,
                )

                Image(
                        modifier = Modifier
                                .clip(CircleShape)
                                .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(),
                                ) {

                                },
                        painter = painterResource(id = R.drawable.ic_more_menu),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(JypColors.Text_white)
                )
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(
                    text = journeyName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = JypColors.Text_white
            )
            Text(
                    text = "$startDay - $endDay",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = JypColors.Text_white,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun MyJourneyScreenEmptyPreview() {
    MyJourneyScreen(
            journeyPropensity = "자유로운 탐험가",
            userName = "다정",
            plannedJourneys = emptyList(),
            pastJourneys = emptyList(),
            onClickNewJourney = {},
            onClickPlanner = {},
    )
}

@Composable
@Preview(showBackground = true)
internal fun MyJourneyScreenPreview() {
    MyJourneyScreen(
            journeyPropensity = "자유로운 탐험가",
            userName = "다정",
            plannedJourneys = listOf(
                    Journey(
                            dDay = "D-3",
                            title = "강릉여행기",
                            theme = 0,
                            startDay = "8월 23일",
                            endDay = "8월 25일",
                            profileUrls = emptyList()
                    ),
                    Journey(
                            dDay = "D-8",
                            title = "즐거운여행기",
                            theme = 1,
                            startDay = "8월 28일",
                            endDay = "8월 30일",
                            profileUrls = emptyList()
                    ),
            ),
            pastJourneys = emptyList(),
            onClickNewJourney = {},
            onClickPlanner = {}
    )
}
