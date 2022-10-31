package com.jyp.feature_planner.presentation.add_planner_route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun AddPlannerRouteScreen() {
    Column(
            modifier = Modifier.fillMaxSize()
    ) {
        SelectedPikis()
        CandidatePikis()
    }
}

@Composable
private fun SelectedPikis() {
    Row(
            modifier = Modifier
                    .height(140.dp)
                    .padding(horizontal = 24.dp)
                    .background(JypColors.Background_white200),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectedPikiItem()
        SelectedPikiItemDivider()
        SelectedPikiItem()
    }
}

@Composable
private fun CandidatePikis() {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(JypColors.Background_white100)
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
        ) {
            JypText(
                    text = "여행 후보 장소",
                    type = TextType.TITLE_6,
                    color = JypColors.Text80,
            )

            LazyColumn {
                item {
                    Spacer(modifier = Modifier.size(24.dp))
                    CandidatePikiItem()
                    Spacer(modifier = Modifier.size(12.dp))
                    CandidatePikiItem()
                    Spacer(modifier = Modifier.size(12.dp))
                    CandidatePikiItem()
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }

        JypTextButton(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                                bottom = 28.dp,
                                start = 24.dp,
                                end = 24.dp,
                        )
                        .align(Alignment.BottomCenter),
                text = "완료하기",
                buttonType = ButtonType.THICK,
                buttonColorSet = ButtonColorSetType.PINK,
                enabled = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedPikiItem() {
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
                    text = "놀거리",
                    type = TextType.BODY_4,
                    color = JypColors.Tag_grey200,
            )
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_3,
                    color = JypColors.Text80,
            )
        }
        Image(
                modifier = Modifier
                        .size(24.dp)
                        .offset(x = 6.dp, y = (-7).dp)
                        .align(Alignment.TopEnd),
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
private fun CandidatePikiItem() {
    Row(
            modifier = Modifier
                    .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(10.dp),
                    )
                    .background(
                            color = JypColors.Background_white100,
                            shape = RoundedCornerShape(10.dp),
                    )
                    .padding(horizontal = 20.dp)
    ) {
        Column(
                modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 11.dp),
        ) {
            JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_5,
                    color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(5.dp))
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
                    painter = painterResource(id = R.drawable.icon_vote_first),
                    contentDescription = null,
            )
            JypText(
                    text = "박물관",
                    type = TextType.BODY_4,
                    color = JypColors.Text40,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddPlannerRouteScreenPreview() {
    AddPlannerRouteScreen(

    )
}