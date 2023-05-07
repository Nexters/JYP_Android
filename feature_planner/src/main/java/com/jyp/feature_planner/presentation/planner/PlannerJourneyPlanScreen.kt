package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.core_network.jyp.model.enumerate.PlaceCategory
import com.jyp.core_network.jyp.model.enumerate.getDrawableResourceId
import com.jyp.core_util.extensions.secondToDate
import com.jyp.feature_planner.R
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.presentation.planner.model.PlanItem
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import java.util.concurrent.TimeUnit

@Composable
internal fun PlannerJourneyPlanScreen(
    planItems: List<PlanItem>,
    startDate: Long,
    onClickRoutePiki: (PlannerPiki) -> Unit,
    onClickEditRoute: (day: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp),
    ) {
        item {
            Spacer(modifier = Modifier.size(30.dp))
        }

        planItems.forEachIndexed { index, planItem ->
            if (planItem.pikis.isEmpty()) {
                item {
                    PlanGroupItem(
                        day = planItem.day,
                        startDate = startDate,
                        onClickEditRoute = {
                            onClickEditRoute.invoke(index)
                        },
                    )
                    Spacer(
                        modifier = Modifier.size(
                            when (index == planItems.lastIndex) {
                                true -> 28.dp
                                false -> 12.dp
                            }
                        )
                    )
                }
            } else {
                if (index > 0 && planItems[index].pikis.isNotEmpty() && planItems[index - 1].pikis.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.size(36.dp))
                    }
                }

                item {
                    PlanEachTitle(
                        day = planItem.day,
                        startDate = startDate,
                        onClickEditRoute = {
                            onClickEditRoute.invoke(index)
                        },
                    )
                    Spacer(modifier = Modifier.size(26.dp))
                }
                planItem.pikis.forEachIndexed { pikisIndex, piki ->
                    item {
                        PlanEachItem(
                            order = pikisIndex + 1,
                            piki = piki,
                            onClickRoutePiki = onClickRoutePiki
                        )
                        if (pikisIndex != planItem.pikis.lastIndex) {
                            PlanEachContentDivider()
                        } else {
                            Spacer(modifier = Modifier.size(48.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanGroupItem(
    day: Int,
    startDate: Long,
    onClickEditRoute: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .border(
                width = 1.dp,
                color = JypColors.Black10,
                shape = RoundedCornerShape(size = 12.dp),
            )
            .padding(horizontal = 20.dp)
            .clickable(onClick = onClickEditRoute),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            JypText(
                text = "Day $day",
                type = TextType.TITLE_6,
                color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(14.dp))
            JypText(
                text = (startDate + TimeUnit.DAYS.toSeconds(day - 1L)).secondToDate("M월 d일"),
                type = TextType.BODY_1,
                color = JypColors.Text40,
            )
        }

        Image(
            modifier = Modifier.size(24.dp),
            painter = JypPainter.add,
            contentDescription = null,
        )
    }
}

@Composable
private fun PlanEachTitle(
    day: Int,
    startDate: Long,
    onClickEditRoute: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            JypText(
                text = "Day $day",
                type = TextType.TITLE_6,
                color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(14.dp))
            JypText(
                text = (startDate + TimeUnit.DAYS.toSeconds(day - 1L)).secondToDate("M월 d일"),
                type = TextType.BODY_1,
                color = JypColors.Text40,
            )
        }
        Image(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickEditRoute,
                ),
            painter = painterResource(id = R.drawable.icon_pencil),
            contentDescription = null,
        )
    }
}

@Composable
private fun PlanEachItem(
    order: Int,
    piki: PlannerPiki,
    onClickRoutePiki: (PlannerPiki) -> Unit
) {
    Row(
        modifier = Modifier.height(81.dp)
    ) {
        PlanEachOrder(
            order = order,
        )
        Spacer(modifier = Modifier.size(12.dp))
        PlanEachContent(
            piki = piki,
            onClickRoutePiki = onClickRoutePiki
        )
    }
}

@Composable
private fun PlanEachOrder(
    order: Int,
) {
    Column {
        JypText(
            modifier = Modifier
                .size(20.dp)
                .background(
                    color = JypColors.Sub_black,
                    shape = CircleShape,
                )
                .padding(top = 1.5.dp),
            text = "$order",
            type = TextType.BODY_4,
            color = JypColors.Text_white,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(
                    color = Color(0xFFE8E8E8)
                )
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun PlanEachContent(
    piki: PlannerPiki,
    onClickRoutePiki: (PlannerPiki) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 4.dp)
            .fillMaxSize()
            .shadow(
                elevation = 32.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = JypColors.Black20
            )
            .background(
                color = JypColors.Background_white100,
                shape = RoundedCornerShape(size = 12.dp),
            )
            .clickable(
                onClick = { onClickRoutePiki(piki) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            JypText(
                text = piki.name,
                type = TextType.TITLE_5,
                color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                text = piki.address,
                type = TextType.BODY_4,
                color = JypColors.Tag_grey200,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(35.dp),
                painter = painterResource(id = piki.categoryIconRes),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                text = piki.category,
                type = TextType.BODY_4,
                color = JypColors.Tag_grey200,
            )
        }
    }
}

@Composable
private fun PlanEachContentDivider() {
    Box(
        modifier = Modifier
            .width(20.dp)
            .height(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(
                    color = Color(0xFFE8E8E8)
                )
                .align(Alignment.Center),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PlannerJourneyPlanScreenPreview() {
    PlannerJourneyPlanScreen(
        planItems = listOf(
            PlanItem(
                day = 1,
                pikis = listOf(
                    PlannerPiki(
                        "아르떼",
                        "우리집",
                        "주소주소",
                        "문화문화",
                        PlaceCategory.R.getDrawableResourceId(),
                        0.0,
                        0.0,
                        ""
                    ),
                    PlannerPiki(
                        "아르떼",
                        "우리집",
                        "주소주소",
                        "문화문화",
                        PlaceCategory.R.getDrawableResourceId(),
                        0.0,
                        0.0,
                        ""
                    ),
                    PlannerPiki(
                        "아르떼",
                        "우리집",
                        "주소주소",
                        "문화문화",
                        PlaceCategory.R.getDrawableResourceId(),
                        0.0,
                        0.0,
                        ""
                    ),
                ),
            ),
            PlanItem(
                day = 2,
                pikis = listOf(),
            ),
            PlanItem(
                day = 3,
                pikis = listOf(
                    PlannerPiki(
                        "아르떼",
                        "우리집",
                        "주소주소",
                        "문화문화",
                        PlaceCategory.R.getDrawableResourceId(),
                        0.0,
                        0.0,
                        ""
                    ),
                    PlannerPiki(
                        "아르떼",
                        "우리집",
                        "주소주소",
                        "문화문화",
                        PlaceCategory.R.getDrawableResourceId(),
                        0.0,
                        0.0,
                        ""
                    ),
                ),
            ),
        ),
        startDate = 1523424321L,
        onClickRoutePiki = {},
        onClickEditRoute = {},
    )
}
