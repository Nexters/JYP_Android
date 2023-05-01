package com.jyp.feature_planner.presentation.add_planner_route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.R
import com.jyp.feature_planner.domain.PlannerPiki
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.launch

@Composable
internal fun AddPlannerRouteScreen(
    pikmis: List<PlannerPikme>,
    pikis: List<PlannerPiki>,
    onSelectPikme: (PlannerPikme) -> Unit,
    onRemovePiki: (PlannerPiki) -> Unit,
    onSubmitPikis: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (pikis.isEmpty()) {
            true -> SelectedPikisEmptyText()
            false -> SelectedPikis(
                pikis = pikis,
                onRemovePiki = onRemovePiki,
                lazyListState = lazyListState
            )
        }

        CandidatePikis(
            modifier = Modifier.weight(1f),
            pikmis = pikmis,
            onSelectPikme = {
                onSelectPikme(it)
                if (pikis.isNotEmpty()) {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(pikis.size - 1)
                    }
                }
            },
        )

        JypTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 28.dp,
                    start = 24.dp,
                    end = 24.dp,
                )
                .align(Alignment.CenterHorizontally),
            text = "완료하기",
            buttonType = ButtonType.THICK,
            buttonColorSet = ButtonColorSetType.PINK,
            enabled = true,
            onClickEnabled = onSubmitPikis,
        )
    }
}

@Composable
private fun SelectedPikis(
    pikis: List<PlannerPiki>,
    onRemovePiki: (PlannerPiki) -> Unit,
    lazyListState: LazyListState
) {
    LazyRow(
        modifier = Modifier
            .height(140.dp)
            .background(JypColors.Background_white200),
        state = lazyListState,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(pikis.size) { index ->
            if (index == 0) {
                Spacer(modifier = Modifier.size(24.dp))
            }
            SelectedPikiItem(
                piki = pikis[idx],
                onRemovePiki = onRemovePiki,
            )

            if (index != pikis.lastIndex) {
                SelectedPikiItemDivider()
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun CandidatePikis(
    pikmis: List<PlannerPikme>,
    onSelectPikme: (PlannerPikme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(JypColors.Background_white100)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            JypText(
                text = stringResource(id = R.string.planner_pik_me_title),
                type = TextType.TITLE_6,
                color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(12.dp))
            LazyColumn {
                items(pikmis) { pikme ->
                    Spacer(modifier = Modifier.size(12.dp))
                    CandidatePikiItem(
                        pikme = pikme,
                        onSelectPikme = onSelectPikme,
                    )
                }

                item {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }
    }
}

@Composable
private fun SelectedPikisEmptyText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        JypText(
            text = "방문할 장소를 선택해 주세요",
            type = TextType.BODY_2,
            modifier = Modifier.align(Alignment.Center),
            color = JypColors.Text40
        )
    }
}

@Composable
private fun SelectedPikiItem(
    piki: PlannerPiki,
    onRemovePiki: (PlannerPiki) -> Unit,
) {
    Box {
        Column(
            modifier = Modifier
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                )
        ) {
            JypText(
                text = piki.category,
                type = TextType.BODY_4,
                color = JypColors.Tag_grey200,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                text = piki.name,
                type = TextType.TITLE_3,
                color = JypColors.Text80,
            )
        }
        Image(
            modifier = Modifier
                .size(24.dp)
                .offset(x = 6.dp, y = (-7).dp)
                .align(Alignment.TopEnd)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onRemovePiki.invoke(piki) },
                ),
            painter = painterResource(id = R.drawable.icon_delete_journey_route),
            contentDescription = null,
        )
    }
}

@Composable
private fun SelectedPikiItemDivider() {
    Box(
        modifier = Modifier
            .size(width = 22.dp, height = 6.dp)
            .background(color = Color(0xFFE6E6E6))
    )
}

@Composable
private fun CandidatePikiItem(
    pikme: PlannerPikme,
    onSelectPikme: (PlannerPikme) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onSelectPikme.invoke(pikme) }
            )
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = DefaultShadowColor.copy(alpha = 0.1f),
            )
            .background(
                color = JypColors.Background_white100,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(start = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 11.dp),
        ) {
            JypText(
                text = pikme.title,
                type = TextType.TITLE_5,
                color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(5.dp))
            JypText(
                text = pikme.address,
                type = TextType.BODY_4,
                color = JypColors.Tag_grey200,
            )
        }
        Column(
            modifier = Modifier.width(72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (pikme.ranking) {
                1 -> R.drawable.icon_vote_first
                2 -> R.drawable.icon_vote_second
                3 -> R.drawable.icon_vote_third
                else -> null

            }.let {
                when (it) {
                    null -> Spacer(modifier = Modifier.size(40.dp))
                    else -> Image(
                        painter = painterResource(id = it),
                        contentDescription = null
                    )
                }
            }
            JypText(
                text = pikme.category,
                type = TextType.BODY_4,
                maxLines = 1,
                color = JypColors.Text40
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddPlannerRouteScreenPreview() {
    AddPlannerRouteScreen(
        pikmis = listOf(
            PlannerPikme("", "아르떼 뮤지엄", "대한민국", "박물관", 3, false, 0.0, 0.0, ""),
            PlannerPikme("", "아르떼 뮤지엄", "대한민국", "박물관", 3, false, 0.0, 0.0, ""),
        ),
        pikis = listOf(
            PlannerPiki(null, "아르떼 뮤지엄", "대한민국", "박물관", 0, 0.0, 0.0, ""),
            PlannerPiki(null, "아르떼 뮤지엄", "대한민국", "박물관", 0, 0.0, 0.0, ""),
            PlannerPiki(null, "아르떼 뮤지엄", "대한민국", "박물관", 0, 0.0, 0.0, ""),
        ),
        onSelectPikme = {},
        onRemovePiki = {},
        onSubmitPikis = {},
    )
}
