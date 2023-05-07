package com.jyp.feature_planner.presentation.edit_tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.tag.TagState
import com.jyp.jyp_design.ui.tag.TagType
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
internal fun EditTagScreen(
    entireTags: List<PlannerTag>,
    onClickBackAction: () -> Unit,
    onClickTagItem: (PlannerTag) -> Unit,
    onClickCreateTag: (TagType) -> Unit,
    onclickEditButton: () -> Unit
) {
    Box {
        GlobalNavigationBarLayout(
            color = GlobalNavigationBarColor.WHITE,
            title = stringResource(id = CreatePlannerStep.TASTE.navigationTitleRes),
            textType = TextType.HEADING_3,
            activeBack = true,
            backAction = onClickBackAction,
        ) {
            Box(
                modifier = Modifier
                    .background(JypColors.Background_white100)
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    EditTagHeader()
                    EditTagContent(
                        modifier = Modifier.weight(1f),
                        entireTags = entireTags,
                        onClickTagItem = onClickTagItem,
                        onClickCreateTag = onClickCreateTag
                    )

                    JypTextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 28.dp),
                        text = "완료하기",
                        buttonType = ButtonType.THICK,
                        buttonColorSet = ButtonColorSetType.PINK,
                        enabled = entireTags.count { it.state == TagState.SELECTED } >= 1,
                        onClickEnabled = onclickEditButton
                    )
                }
            }
        }
    }
}

@Composable
internal fun EditTagHeader(
) {
    Column {
        JypText(
            text = stringResource(id = CreatePlannerStep.TASTE.titleRes),
            type = TextType.TITLE_1,
            color = JypColors.Text90
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
            text = stringResource(id = CreatePlannerStep.TASTE.descriptionRes),
            type = TextType.BODY_2,
            color = JypColors.Text40
        )
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun EditTagContent(
    modifier: Modifier,
    entireTags: List<PlannerTag>,
    onClickTagItem: (PlannerTag) -> Unit,
    onClickCreateTag: (TagType) -> Unit
) {
    val sosoTags = entireTags.filter { tag -> tag.type is TagType.Soso }
    val likeTags = entireTags.filter { tag -> tag.type is TagType.Like }
    val dislikeTags = entireTags.filter { tag -> tag.type is TagType.Dislike }

    Column(
        modifier = modifier
    ) {
        TastesSection(
            tagCategory = "상관 없어요 태그",
            tags = sosoTags,
            onClickTagItem = onClickTagItem
        )
        Spacer(modifier = Modifier.size(40.dp))
        TastesSection(
            tagCategory = "좋아요 태그",
            tags = likeTags,
            onClickTagItem = onClickTagItem,
            onClickCreateTag = { onClickCreateTag.invoke(TagType.Like) }
        )
        Spacer(modifier = Modifier.size(40.dp))
        TastesSection(
            tagCategory = "싫어요 태그",
            tags = dislikeTags,
            onClickTagItem = onClickTagItem,
            onClickCreateTag = { onClickCreateTag.invoke(TagType.Dislike) }
        )
    }
}

@Composable
private fun TastesSection(
    tagCategory: String,
    tags: List<PlannerTag>,
    onClickTagItem: (PlannerTag) -> Unit,
    onClickCreateTag: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            JypText(
                text = tagCategory,
                type = TextType.TITLE_6,
                color = JypColors.Text90
            )

            onClickCreateTag?.let {
                Image(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClickCreateTag
                    ),
                    painter = JypPainter.add,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        FlowRow {
            FlowRow(
                crossAxisSpacing = 12.dp,
                mainAxisSpacing = 8.dp
            ) {
                tags.forEach { tag ->
                    DecoratedTag(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onClickTagItem.invoke(tag) }
                        ),
                        tagType = tag.type,
                        tagState = tag.state,
                        content = tag.content,
                        tagCount = 0
                    )
                }
            }
        }
    }
}