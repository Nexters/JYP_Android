package com.jyp.feature_planner.presentation.planner

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.jyp.feature_planner.R
import com.jyp.feature_planner.domain.PikMe
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBar
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.tag.TagType
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlannerScreen(
        pikMes: List<PikMe>,
        tags: List<Tag>,
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
                PlannerBackLayer()
            },
            frontLayerContent = {
                PlannerContent(
                        tabTitles = listOf(
                                stringResource(id = R.string.planner_tab_forum),
                                stringResource(id = R.string.planner_tab_piki),
                        ),
                        selectedTabPosition = selectedTabPosition,
                        tabSelected = { selectedTabPosition = it },
                        pikMes = pikMes,
                        tags = tags,
                )
            },
            backLayerBackgroundColor = JypColors.Background_grey300,
            frontLayerScrimColor = Color.Unspecified,
    )
}

@Composable
private fun PlannerBackLayer() {
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
        Button(
                modifier = Modifier
                        .height(40.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(backgroundColor = JypColors.Pink),
                contentPadding = PaddingValues(0.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.icon_smile_plus),
                    contentDescription = null,
            )
            Spacer(modifier = Modifier.size(2.dp))
            JypText(
                    text = "일행 초대하기",
                    type = TextType.BODY_3,
                    color = JypColors.Text_white,
            )
            Spacer(modifier = Modifier.size(10.dp))
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
            0 -> PlannerForumContent(
                    pikMes = pikMes,
                    tags = tags,
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
private fun PlannerForumContent(
        pikMes: List<PikMe>,
        tags: List<Tag>,
) {
    val rememberScrollState = rememberScrollState()

    Column(
            modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        PlannerJourneyTagContent(tags = tags)
        Spacer(modifier = Modifier.size(48.dp))
        PlannerPikMeContent(pikMes = pikMes)
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun PlannerJourneyTagContent(
        tags: List<Tag>,
) {
    var isCollapsed by remember {
        mutableStateOf(false)
    }

    val rotateAnimate by animateFloatAsState(
            targetValue = if (isCollapsed) {
                180f
            } else {
                0f
            }
    )

    Column {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            JypText(
                    text = stringResource(id = R.string.planner_journey_tags_title),
                    type = TextType.TITLE_6,
                    color = JypColors.Text80,
            )

            Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                        painter = painterResource(id = R.drawable.icon_pencil),
                        contentDescription = null,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                        modifier = Modifier
                                .clip(CircleShape)
                                .rotate(rotateAnimate)
                                .clickable { isCollapsed = !isCollapsed },
                        painter = painterResource(id = R.drawable.icon_arrow_top),
                        contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        JypText(
                text = stringResource(id = R.string.planner_journey_tags_description),
                type = TextType.BODY_3,
                color = JypColors.Text40,
        )
        AnimatedVisibility(
                visible = !isCollapsed,
                enter = expandVertically(),
                exit = shrinkVertically(),
        ) {
            Column {
                Spacer(modifier = Modifier.size(16.dp))
                PlannerTagLayout(
                        tags = tags,
                )
            }
        }
    }
}

@Composable
private fun PlannerTagLayout(
        tags: List<Tag> = emptyList(),
) {
    FlowRow(
            crossAxisSpacing = 12.dp,
            mainAxisSpacing = 8.dp,
    ) {
        tags.forEach { tag ->
            DecoratedTag(
                    tagType = tag.type,
                    tagState = tag.state,
                    content = tag.content,
                    tagCount = 1,
            )
        }
    }
}

@Composable
private fun PlannerPikMeContent(
        pikMes: List<PikMe>,
) {
    Column {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            JypText(
                    text = stringResource(id = R.string.planner_pik_me_title),
                    type = TextType.TITLE_6,
                    color = JypColors.Text80,
            )

            Image(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        JypText(
                text = stringResource(id = R.string.planner_pik_me_description),
                type = TextType.BODY_3,
                color = JypColors.Text40,
        )
        Spacer(modifier = Modifier.size(20.dp))

        if (pikMes.isEmpty()) {
            PlannerPikMeEmptyCard()
        } else {
            pikMes.forEach { pikMe ->
                PlannerPikMeCard(pikMe = pikMe)
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun PlannerPikMeCard(pikMe: PikMe) {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(12.dp),
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(JypColors.Background_white100)
                    .padding(20.dp),
    ) {
        Column(
                modifier = Modifier
                        .size(62.dp)
                        .shadow(
                                elevation = 2.dp,
                                shape = CircleShape,
                        )
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd)
                        .background(JypColors.Background_white100),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                    modifier = Modifier.size(width = 30.dp, height = 24.dp),
                    painter = painterResource(id = R.drawable.icon_heart_like),
                    contentDescription = null,
            )

            if (pikMe.likeCount > 0) {
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                        text = pikMe.likeCount.toString(),
                        color = JypColors.Pink,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Column {
            JypText(
                    text = pikMe.category,
                    type = TextType.BODY_4,
                    color = JypColors.Tag_grey200,
            )
            Spacer(modifier = Modifier.size(7.dp))
            JypText(
                    text = pikMe.title,
                    type = TextType.HEADING_2,
                    color = JypColors.Text80,
            )
            JypText(
                    text = pikMe.address,
                    type = TextType.BODY_4,
                    color = JypColors.Tag_grey200,
            )
            Spacer(modifier = Modifier.size(14.dp))
            Row(
                    modifier = Modifier
                            .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(8.dp),
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .background(JypColors.Background_white100)
                            .padding(2.dp),
            ) {
                Spacer(modifier = Modifier.size(2.dp))
                Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.icon_eyes),
                        contentDescription = null,
                )
                Spacer(modifier = Modifier.size(3.dp))
                JypText(
                        modifier = Modifier.padding(
                                top = 6.dp,
                                end = 6.dp,
                                bottom = 6.dp,
                        ),
                        text = "정보 보기",
                        type = TextType.BODY_1,
                        color = JypColors.Text80,
                )
            }
        }
    }
}

@Composable
private fun PlannerPikMeEmptyCard() {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(16.dp),
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(JypColors.Background_white100),
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        Image(
                painter = painterResource(id = R.drawable.icon_pik_me_empty),
                contentDescription = null,
        )
        Spacer(modifier = Modifier.size(24.dp))
        JypTextButton(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                text = "후보 장소 추가하기",
                buttonType = ButtonType.MEDIUM,
                buttonColorSet = ButtonColorSetType.BLACK,
                enabled = true,
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlannerScreenPreview() {
    PlannerScreen(
            pikMes = emptyList(),
            tags = emptyList(),
    )
}
