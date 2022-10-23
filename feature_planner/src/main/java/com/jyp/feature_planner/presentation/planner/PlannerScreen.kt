package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_planner.R.*
import com.jyp.feature_planner.domain.PikMe
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.AvatarList
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBar
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlannerScreen(
        pikMes: List<PikMe>,
        joinMembers: List<String>,
        tags: List<Tag>,
        tagClick: (Tag) -> Unit,
        newPikMeClick: () -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    var selectedTabPosition by remember {
        mutableStateOf(0)
    }

    BackdropScaffold(
            scaffoldState = scaffoldState,
            appBar = {
                GlobalNavigationBar(
                        color = GlobalNavigationBarColor.BLACK,
                        title = "강릉 여행기",
                        activeBack = true,
                )
            },
            backLayerContent = {
                PlannerBackLayer(
                        profileImageUrls = joinMembers,
                )
            },
            frontLayerContent = {
                PlannerContent(
                        tabTitles = listOf(
                                stringResource(id = string.planner_tab_forum),
                                stringResource(id = string.planner_tab_piki),
                        ),
                        selectedTabPosition = selectedTabPosition,
                        tabSelected = { selectedTabPosition = it },
                        pikMes = pikMes,
                        tags = tags,
                        tagClick = tagClick,
                        newPikMeClick = newPikMeClick,
                )
            },
            backLayerBackgroundColor = JypColors.Background_grey300,
            frontLayerScrimColor = Color.Unspecified,
    )
}

@Composable
private fun PlannerBackLayer(
        profileImageUrls: List<String>,
) {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        JypText(
                text = "7월 18일 - 7월 20일",
                type = TextType.BODY_1,
                color = JypColors.Text_white,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Box {
            AvatarList(
                    profileImageUrls = profileImageUrls,
                    width = 44.dp,
                    height = 44.dp,
                    borderColor = JypColors.Background_grey300,
            ) {
                Image(
                        modifier = Modifier.size(52.dp),
                        painter = painterResource(id = drawable.icon_invite_small),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun PlannerContent(
        tabTitles: List<String>,
        selectedTabPosition: Int,
        tabSelected: (position: Int) -> Unit,
        pikMes: List<PikMe>,
        tags: List<Tag>,
        tagClick: (Tag) -> Unit,
        newPikMeClick: () -> Unit,
) {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .clip(
                            RoundedCornerShape(
                                    topStart = 24.dp,
                                    topEnd = 24.dp,
                            )
                    )
                    .background(JypColors.Background_white100),
    ) {
        PlannerContentTab(
                tabTitles,
                selectedTabPosition = selectedTabPosition,
                tabSelected = tabSelected,
        )

        Spacer(modifier = Modifier.size(4.dp))

        when (selectedTabPosition) {
            0 -> PlannerForumScreen(
                    pikMes = pikMes,
                    tags = tags,
                    tagClick = tagClick,
                    newPikMeClick = newPikMeClick,
            )
            1 -> PlannerJourneyPlanScreen(

            )
        }
    }
}

@Composable
private fun PlannerContentTab(
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
@Preview(showBackground = true)
internal fun PlannerScreenPreview() {
    PlannerScreen(
            pikMes = emptyList(),
            joinMembers = emptyList(),
            tags = emptyList(),
            tagClick = {},
            newPikMeClick = {},
    )
}
