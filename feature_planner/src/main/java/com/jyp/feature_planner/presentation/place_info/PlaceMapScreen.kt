package com.jyp.feature_planner.presentation.place_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
internal fun PlaceMapScreen() {
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
                onClick = {} // Todo - Set click event for back button.
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
            Row(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = JypColors.Background_white200,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = JypColors.Border_grey,
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {
                JypText(
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_3,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .padding(start = 12.dp)
                        .weight(weight = 1f)
                    ,
                    color = JypColors.Text80
                )
                Spacer(modifier = Modifier.size(12.dp))
                Image(
                    painter = painterResource(id = com.jyp.jyp_design.R.drawable.ic_text_delete),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(end = 12.dp)
                        .wrapContentSize()
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top,
                ) {
                    Button(
                        onClick = {}, // Todo - Set click event for info button.
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = 20.dp)
                            .padding(horizontal = 24.dp),
                        enabled = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = JypColors.Background_white100
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_eyes),
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        JypText(
                            text = "정보 보기",
                            type = TextType.BODY_1,
                            modifier = Modifier.padding(all = 8.dp),
                            color = JypColors.Text90,
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
                    enabled = true,
                    buttonColorSet = ButtonColorSetType.PINK
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlaceMapScreenPreview() {
    PlaceMapScreen()
}
