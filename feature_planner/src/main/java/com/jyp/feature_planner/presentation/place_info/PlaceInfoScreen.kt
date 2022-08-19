package com.jyp.feature_planner.presentation.place_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jyp.feature_planner.R
import com.jyp.feature_planner.presentation.planner.PlannerScreen
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
internal fun PlaceInfoScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(com.jyp.jyp_design.R.drawable.icon_left_arrow),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 14.dp
                    ),
                    tint = JypColors.Sub_black
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 24.dp,
                    )
            ) {
                JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_3,
                    color = JypColors.Text80
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_map),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(alignment = Alignment.BottomCenter)
                    .background(
                        color = JypColors.Background_white100,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    ),
                verticalArrangement = Arrangement.Bottom
            ) {
                JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_1,
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 24.dp),
                    color = JypColors.Text90
                )
                JypText(
                    text = "강원 강릉시 난설헌로 131",
                    type = TextType.BODY_3,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .padding(horizontal = 24.dp),
                    color = JypColors.Text40
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = JypColors.Background_white100,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(8.dp),
                                ambientColor = JypColors.Black20.copy(alpha = 0.001f)
                            )
                            .zIndex(1f)
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_eyes),
                            contentDescription = null
                        )
                        JypText(
                            text = "정보 보기",
                            type = TextType.BODY_1,
                            color = JypColors.Text90
                        )
                    }
                }
                JypTextButton(
                    text = "추가하기",
                    buttonType = ButtonType.THICK,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 28.dp),
                    buttonColorSet = ButtonColorSetType.PINK
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlaceInfoScreenPreview() {
    PlaceInfoScreen()
}
