package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_planner.domain.Pikis
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun PlannerJourneyPlanScreen(
        planItems: List<PlanItem> = listOf(
                PlanItem(
                        day = 1,
                        pikis = listOf(
                                Pikis("아르떼", "우리집"),
                                Pikis("아르떼", "우리집"),
                                Pikis("아르떼", "우리집"),
                        ),
                ),
                PlanItem(
                        day = 2,
                        pikis = listOf(),
                ),
                PlanItem(
                        day = 3,
                        pikis = listOf(
                                Pikis("아르떼", "우리집"),
                                Pikis("아르떼", "우리집"),
                        ),
                ),
        ),
        onClickEditRoute: () -> Unit,
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
                            onClickEditRoute = onClickEditRoute,
                    )
                    if (index != planItems.lastIndex) {
                        Spacer(modifier = Modifier.size(12.dp))
                    }
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
                    )
                    Spacer(modifier = Modifier.size(26.dp))
                }
                planItem.pikis.forEachIndexed { pikisIndex, pikis ->
                    item {
                        PlanEachItem(
                                order = pikisIndex + 1,
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
        onClickEditRoute: () -> Unit
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
                    text = "7월 18일",
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
) {
    Row {
        JypText(
                text = "Day $day",
                type = TextType.TITLE_6,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(14.dp))
        JypText(
                text = "mm월 dd일",
                type = TextType.BODY_1,
                color = JypColors.Text40,
        )
    }
}

@Composable
private fun PlanEachItem(
        order: Int,
) {
    Row(
            modifier = Modifier
                    .height(81.dp)
    ) {
        PlanEachOrder(
                order = order,
        )
        Spacer(modifier = Modifier.size(12.dp))
        PlanEachContent()
    }
}

@Composable
private fun PlanEachOrder(
        order: Int,
) {
    Column {
        Text(
                modifier = Modifier
                        .size(20.dp)
                        .background(
                                color = JypColors.Sub_black,
                                shape = CircleShape,
                        ),
                text = "$order",
                fontWeight = FontWeight.SemiBold,
                color = JypColors.Text_white,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
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
private fun PlanEachContent() {
    Row(
            modifier = Modifier
                    .padding(end = 4.dp)
                    .fillMaxSize()
                    .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(size = 12.dp),
                    )
                    .background(
                            color = JypColors.Background_white100,
                            shape = RoundedCornerShape(size = 12.dp),
                    )
                    .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_5,
                    color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                    text = "강원 강릉시 난설헌로 131",
                    type = TextType.BODY_4,
                    color = JypColors.Tag_grey200,
            )
        }
        Column(
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                    modifier = Modifier.size(35.dp),
                    painter = JypPainter.CulturalInstitution,
                    contentDescription = null,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                    text = "문화시설",
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
            onClickEditRoute = {},
    )
}
