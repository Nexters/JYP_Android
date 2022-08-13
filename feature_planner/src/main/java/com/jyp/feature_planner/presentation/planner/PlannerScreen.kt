package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBar
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.tag.TagType
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlannerScreen() {
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
                        tabSelected = { selectedTabPosition = it }
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

        when (selectedTabPosition) {
            0 -> PlannerForumContent()
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
private fun PlannerForumContent() {
    Column(
            modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(28.dp))
        PlannerJourneyTagContent()
    }
}

@Composable
private fun PlannerJourneyTagContent() {
    var isCollapsed by remember {
        mutableStateOf(false)
    }

    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        JypText(
                text = stringResource(id = R.string.planner_journey_tags),
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
                            .clickable { isCollapsed = !isCollapsed },
                    painter = painterResource(id = R.drawable.icon_arrow_top),
                    contentDescription = null,
            )
        }
    }
    Spacer(modifier = Modifier.size(6.dp))
    JypText(
            text = "여행 취향을 확인해보세요!",
            type = TextType.BODY_3,
            color = JypColors.Text40,
    )
    if (!isCollapsed) {
        Spacer(modifier = Modifier.size(16.dp))
        PlannerTagLayout(
                tags = listOf(
                        Tag(TagType.Like(), "시러시러"),
                        Tag(TagType.Like(), "시러허허"),
                        Tag(TagType.Like(), "좋아"),
                        Tag(TagType.Like(), "싫어싫"),
                        Tag(TagType.Dislike(), "조아아"),
                        Tag(TagType.Dislike(), "좋아"),
                        Tag(TagType.Dislike(), "시러머버더거서ㅛㅓ"),
                        Tag(TagType.Soso(), "상관업"),
                )
        )
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
                    content = tag.content,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlannerScreenPreview() {
    PlannerScreen()
}
