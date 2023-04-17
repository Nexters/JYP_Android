package com.jyp.feature_planner.presentation.planner

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.google.accompanist.flowlayout.FlowRow
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun PlannerForumScreen(
    pikMes: List<PlannerPikme>,
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    newPikMeClick: () -> Unit,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
    onClickEditTag: () -> Unit,
) {
    val rememberScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        PlannerJourneyTagContent(
            tags = tags,
            tagClick = tagClick,
            onClickEditTag = onClickEditTag,
        )
        Spacer(modifier = Modifier.size(48.dp))
        PlannerPikMeContent(
            pikMes = pikMes,
            newPikMeClick = newPikMeClick,
            onClickInfo = onClickInfo,
            onClickLike = onClickLike,
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun PlannerJourneyTagContent(
    tags: List<PlannerTag>,
    tagClick: (PlannerTag) -> Unit,
    onClickEditTag: () -> Unit,
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
                text = stringResource(id = com.jyp.feature_planner.R.string.planner_journey_tags_title),
                type = TextType.TITLE_6,
                color = JypColors.Text80,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    modifier = Modifier
                        .clickable { onClickEditTag.invoke() },
                    painter = painterResource(id = com.jyp.feature_planner.R.drawable.icon_pencil),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .rotate(rotateAnimate)
                        .clickable { isCollapsed = !isCollapsed },
                    painter = painterResource(id = com.jyp.feature_planner.R.drawable.icon_arrow_top),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        JypText(
            text = stringResource(id = com.jyp.feature_planner.R.string.planner_journey_tags_description),
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
                    tagClick = tagClick,
                )
            }
        }
    }
}

@Composable
private fun PlannerTagLayout(
    tags: List<PlannerTag> = emptyList(),
    tagClick: (PlannerTag) -> Unit,
) {
    FlowRow(
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp,
    ) {
        tags.forEach { tag ->
            DecoratedTag(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { tagClick.invoke(tag) }
                    ),
                tagType = tag.type,
                tagState = tag.state,
                content = tag.content,
                tagCount = tag.selectPeople.size,
            )
        }
    }
}

@Composable
private fun PlannerPikMeContent(
    pikMes: List<PlannerPikme>,
    newPikMeClick: () -> Unit,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            JypText(
                text = stringResource(id = com.jyp.feature_planner.R.string.planner_pik_me_title),
                type = TextType.TITLE_6,
                color = JypColors.Text80,
            )

            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = newPikMeClick
                    ),
                painter = painterResource(id = com.jyp.feature_planner.R.drawable.icon_add),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        JypText(
            text = stringResource(id = com.jyp.feature_planner.R.string.planner_pik_me_description),
            type = TextType.BODY_3,
            color = JypColors.Text40,
        )
        Spacer(modifier = Modifier.size(20.dp))

        if (pikMes.isEmpty()) {
            PlannerPikMeEmptyCard(
                newPikMeClick = newPikMeClick,
            )
        } else {
            pikMes.forEach { pikMe ->
                PlannerPikMeCard(
                    pikMe = pikMe,
                    onClickInfo = onClickInfo,
                    onClickLike = onClickLike,
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun PlannerPikMeCard(
    pikMe: PlannerPikme,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
) {
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
        var initialLiked by rememberSaveable {
            mutableStateOf(pikMe.liked)
        }

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.jyp.feature_planner.R.raw.like_alone_alpha))
        val lottieAnimatable = rememberLottieAnimatable()

        LaunchedEffect(pikMe.liked) {
            if (pikMe.liked) {
                if (initialLiked) {
                    lottieAnimatable.snapTo(composition, 1f)
                    initialLiked = false
                } else {
                    lottieAnimatable.animate(composition)
                }
            } else {
                lottieAnimatable.snapTo(composition, 0f)
            }
        }

        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClickLike.invoke(pikMe) }
                )
                .size(62.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .background(JypColors.Background_white100),
            contentAlignment = Alignment.Center,
        ) {
            LottieAnimation(
                composition = composition,
                progress = { lottieAnimatable.progress },
            )

            if (pikMe.liked) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
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
                    .padding(2.dp)
                    .clickable { onClickInfo(pikMe) }
            ) {
                Spacer(modifier = Modifier.size(2.dp))
                Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = com.jyp.feature_planner.R.drawable.icon_eyes),
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
private fun PlannerPikMeEmptyCard(
    newPikMeClick: () -> Unit,
) {
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
            painter = painterResource(id = com.jyp.feature_planner.R.drawable.icon_pik_me_empty),
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
            onClickEnabled = newPikMeClick,
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun PlannerForumScreenPreview() {
    PlannerForumScreen(
        pikMes = emptyList(),
        tags = emptyList(),
        tagClick = {},
        newPikMeClick = {},
        onClickInfo = {},
        onClickLike = {},
        onClickEditTag = {},
    )
}
