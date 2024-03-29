package com.jyp.feature_planner.presentation.planner

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.google.accompanist.flowlayout.FlowRow
import com.jyp.feature_planner.R
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun PlannerForumScreen(
    pikMis: List<PlannerPikme>,
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
            pikMis = pikMis,
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
    pikMis: List<PlannerPikme>,
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

        if (pikMis.isEmpty()) {
            PlannerPikMeEmptyCard(
                newPikMeClick = newPikMeClick,
            )
        } else {
            pikMis.forEach { pikMe ->
                PlannerPikMeCard(
                    pikMe = pikMe,
                    onClickInfo = onClickInfo,
                    onClickLike = onClickLike,
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
private fun PlannerPikMeCard(
    pikMe: PlannerPikme,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 30.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = DefaultShadowColor.copy(alpha = 0.1f),
            )
            .clip(RoundedCornerShape(12.dp))
            .background(JypColors.Background_white100)
    ) {
        var initialLiked by rememberSaveable {
            mutableStateOf(pikMe.liked)
        }
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.like_alone_alpha)
        )
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
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
            PlaceInfoButton(
                modifier = Modifier.wrapContentSize( ),
                onClick = { onClickInfo(pikMe) }
            )
        }
        when (pikMe.ranking) {
            1 -> R.drawable.icon_vote_first
            2 -> R.drawable.icon_vote_second
            3 -> R.drawable.icon_vote_third
            else -> null

        }?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 30.dp)
            )
        }
        PlannerPikMeLikeButton(
            modifier = Modifier
                .padding(all = 20.dp)
                .align(Alignment.BottomEnd),
            pikMe = pikMe,
            onClickLike = onClickLike,
            composition = composition,
            lottieAnimatable = lottieAnimatable
        )
    }
}

@Composable
private fun PlannerPikMeLikeButton(
    modifier: Modifier,
    pikMe: PlannerPikme,
    onClickLike: (PlannerPikme) -> Unit,
    composition: LottieComposition?,
    lottieAnimatable: LottieAnimatable
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClickLike.invoke(pikMe) }
            )
            .size(62.dp)
            .shadow(
                elevation = 2.dp,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(JypColors.Background_white100),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { lottieAnimatable.progress }
        )

        if (pikMe.liked) {
            JypText(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                text = pikMe.likeCount.toString(),
                type = TextType.BODY_4,
                color = JypColors.Pink
            )
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
                elevation = 30.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DefaultShadowColor.copy(alpha = 0.1f),
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
        pikMis = emptyList(),
        tags = emptyList(),
        tagClick = {},
        newPikMeClick = {},
        onClickInfo = {},
        onClickLike = {},
        onClickEditTag = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun PlannerPikMeCardPreview() {
    PlannerPikMeCard(
        PlannerPikme(
            id = "",
            title = "title",
            address = "",
            category = "마트",
            likeCount = 3,
            liked = true,
            longitude = 0.0,
            latitude = 0.0,
            link = "",
            ranking = 1
        ),
        onClickInfo = {},
        onClickLike = {}
    )
}
