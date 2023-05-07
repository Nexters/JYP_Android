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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.core_util.extensions.secondToDate
import com.jyp.feature_planner.R.*
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.presentation.planner.model.PlanItem
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.AvatarList
import com.jyp.jyp_design.ui.avatar.AvatarType
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBar
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlannerScreen(
    isDDay: Boolean,
    plannerTitle: String,
    startDate: Long,
    endDate: Long,
    pikMis: List<PlannerPikme>,
    joinMembers: List<String>,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    planItems: List<PlanItem>,
    newPikMeClick: () -> Unit,
    onClickRoutePiki: (PlannerPiki) -> Unit,
    onClickEditRoute: (day: Int) -> Unit,
    onClickInviteUserButton: () -> Unit,
    onClickBackButton: () -> Unit,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
    onClickEditTag: () -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    var selectedTabPosition by remember {
        when (isDDay) {
            true -> mutableStateOf(1)
            false -> mutableStateOf(0)
        }
    }

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            GlobalNavigationBar(
                color = GlobalNavigationBarColor.BLACK,
                title = plannerTitle,
                textType = TextType.HEADING_2,
                activeBack = true,
                backAction = onClickBackButton,
            )
        },
        backLayerContent = {
            PlannerBackLayer(
                startDate = startDate,
                endDate = endDate,
                profileImageUrls = joinMembers,
                onClickInviteUserButton = onClickInviteUserButton,
            )
        },
        frontLayerContent = {
            PlannerContent(
                tabTitles = listOf(
                    stringResource(id = string.planner_tab_forum),
                    stringResource(id = string.planner_tab_piki),
                ),
                startDate = startDate,
                selectedTabPosition = selectedTabPosition,
                tabSelected = { selectedTabPosition = it },
                pikMis = pikMis,
                planItems = planItems,
                tags = tags,
                tagClick = tagClick,
                newPikMeClick = newPikMeClick,
                onClickRoutePiki = onClickRoutePiki,
                onClickEditRoute = onClickEditRoute,
                onClickInfo = onClickInfo,
                onClickLike = onClickLike,
                onClickEditTag = onClickEditTag,
            )
        },
        backLayerBackgroundColor = JypColors.Background_grey300,
        frontLayerScrimColor = Color.Unspecified,
    )
}

@Composable
private fun PlannerBackLayer(
    startDate: Long,
    endDate: Long,
    profileImageUrls: List<String>,
    onClickInviteUserButton: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        JypText(
            text = "${startDate.secondToDate("M월 d일")} - ${endDate.secondToDate("M월 d일")}",
            type = TextType.BODY_1,
            color = JypColors.Text_white,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Box {
            AvatarList(
                profileImageUrls = profileImageUrls,
                avatarType = AvatarType.SMALL,
                borderColor = JypColors.Background_grey300,
            ) {
                Image(
                    modifier = Modifier
                        .size(AvatarType.SMALL.size.dp)
                        .padding(all = 1.5.dp)
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(AvatarType.SMALL.radius.dp),
                            ambientColor = Color.Black,
                            spotColor = Color.Black
                        )
                        .clickable { onClickInviteUserButton() },
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
    startDate: Long,
    selectedTabPosition: Int,
    tabSelected: (position: Int) -> Unit,
    pikMis: List<PlannerPikme>,
    planItems: List<PlanItem>,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    newPikMeClick: () -> Unit,
    onClickRoutePiki: (PlannerPiki) -> Unit,
    onClickEditRoute: (day: Int) -> Unit,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
    onClickEditTag: () -> Unit,
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
                pikMis = pikMis,
                tags = tags,
                tagClick = tagClick,
                newPikMeClick = newPikMeClick,
                onClickInfo = onClickInfo,
                onClickLike = onClickLike,
                onClickEditTag = onClickEditTag,
            )
            1 -> PlannerJourneyPlanScreen(
                planItems = planItems,
                startDate = startDate,
                onClickRoutePiki = onClickRoutePiki,
                onClickEditRoute = onClickEditRoute
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
            JypText(
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
                type = TextType.TITLE_3,
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
        isDDay = true,
        plannerTitle = "무슨 무슨 여행기~",
        startDate = 21312412L,
        endDate = 21312412L,
        pikMis = emptyList(),
        joinMembers = emptyList(),
        tags = emptyList(),
        tagClick = {},
        planItems = emptyList(),
        newPikMeClick = {},
        onClickInviteUserButton = {},
        onClickRoutePiki = {},
        onClickEditRoute = {},
        onClickBackButton = {},
        onClickInfo = {},
        onClickLike = {},
        onClickEditTag = {},
    )
}
