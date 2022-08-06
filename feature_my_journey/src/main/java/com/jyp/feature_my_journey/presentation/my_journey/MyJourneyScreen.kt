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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_my_journey.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.shadow.drawShadow

@Composable
fun MyJourneyScreen(
        journeyPropensity: String,
        userName: String,
        journeys: List<String>,
        onClickNewJourney: () -> Unit,
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
                journeys = journeys,
                onClickNewJourney = onClickNewJourney,
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
        journeys: List<String>,
        onClickNewJourney: () -> Unit,
) {
    Column(
            modifier = Modifier
                    .background(JypColors.Background_white100),
    ) {
        MyJourneyContentTab()
        PlannedJourney(
                journeys = journeys,
                onClickNewJourney = onClickNewJourney,
        )
    }
}

@Composable
internal fun MyJourneyContentTab() {
    Row(
            modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 20.dp)
    ) {
        Text(
                modifier = Modifier
                        .drawWithContent {
                            drawContent()
                            drawRoundRect(
                                    color = JypColors.Text80,
                                    topLeft = Offset(0f, size.height + 4.dp.toPx()),
                                    cornerRadius = CornerRadius(x = 16.dp.toPx(), y = 16.dp.toPx()),
                            )
                        }
                        .padding(vertical = 6.dp),
                text = stringResource(id = R.string.my_journey_planned_journey),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = JypColors.Text80,
        )
    }
}

@Composable
internal fun PlannedJourney(
        journeys: List<String>,
        onClickNewJourney: () -> Unit,
) {
    if (journeys.isEmpty()) {
        PlannedJourneyEmptyScreen(onClickNewJourney = onClickNewJourney)
    } else {
        LazyRow(
                contentPadding = PaddingValues(start = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            journeys.forEach { journey ->
                item {
                    PlannedJourneyItem(
                            journeyName = journey,
                            startDay = "7월 18일",
                            endDay = "7월 28일",
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
                Box(
                        modifier = Modifier
                                .background(JypColors.Tag_grey200)
                                .size(120.dp)
                )
                Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(id = R.string.my_journey_suggest_new_journey),
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
internal fun PlannedJourneyItem(
        journeyName: String,
        startDay: String,
        endDay: String,
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
        Row(
                modifier = Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(modifier = Modifier
                    .size(width = 30.dp, height = 20.dp)
                    .background(JypColors.Sub_blue200))

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
            )
        }

        Text(
                modifier = Modifier.padding(top = 16.dp),
                text = journeyName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = JypColors.Text80
        )
        Text(
                text = "$startDay - $endDay",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = JypColors.Text75,
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun MyJourneyScreenEmptyPreview() {
    MyJourneyScreen(
            journeyPropensity = "자유로운 탐험가",
            userName = "다정",
            journeys = emptyList(),
            onClickNewJourney = {},
    )
}

@Composable
@Preview(showBackground = true)
internal fun MyJourneyScreenPreview() {
    MyJourneyScreen(
            journeyPropensity = "자유로운 탐험가",
            userName = "다정",
            journeys = listOf("강릉여행기", "집앞여행기"),
            onClickNewJourney = {}
    )
}
