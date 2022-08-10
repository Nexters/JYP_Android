package com.jyp.feature_my_page.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.Avatar
import com.jyp.jyp_design.ui.text.*
import com.jyp.jyp_design.ui.typography.type.*
import kotlinx.coroutines.flow.flow

@Composable
fun MyPageScreen(
        journeyPropensity: String,
) {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(JypColors.Background_white100),
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(197.dp)
                        .background(JypColors.Background_white200),
        )
        Column(
                modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxSize(),
        ) {
            Text(
                    modifier = Modifier
                            .padding(top = 8.dp),
                    text = journeyPropensity,
                    type = TextType.HEADING_1,
                    textAlign = TextAlign.Start,
                    color = JypColors.Text90,
            )

            Spacer(modifier = Modifier.size(100.dp))

            Profile()

            Spacer(modifier = Modifier.size(40.dp))

            Menu()
        }
    }
}

@Composable
private fun Profile() {
    Column(
            modifier = Modifier.fillMaxWidth(),
    ) {
        Avatar(
                modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                profileImageUrl = "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                width = 88.dp,
                height = 88.dp,
                showBorder = false,
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
                modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                text = "박진영",
                type = TextType.TITLE_2,
                textAlign = TextAlign.Start,
                color = JypColors.Text90,
        )
    }
}

@Composable
internal fun Menu() {
    Column {
        Text(
                modifier = Modifier.padding(start = 14.dp),
                text = "공지사항",
                type = TextType.TITLE_6,
                textAlign = TextAlign.Start,
                color = JypColors.Text80,
        )

        Spacer(modifier = Modifier.size(12.dp))
        Divider()
        Spacer(modifier = Modifier.size(12.dp))

        Text(
                modifier = Modifier.padding(start = 14.dp),
                text = "이용약관",
                type = TextType.TITLE_6,
                textAlign = TextAlign.Start,
                color = JypColors.Text80,
        )

        Spacer(modifier = Modifier.size(12.dp))
        Divider()
        Spacer(modifier = Modifier.size(12.dp))

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                    modifier = Modifier.padding(start = 14.dp),
                    text = "버전정보",
                    type = TextType.TITLE_6,
                    textAlign = TextAlign.Start,
                    color = JypColors.Text80,
            )

            Text(
                    modifier = Modifier.padding(end = 14.dp),
                    text = "1.0",
                    type = TextType.TITLE_6,
                    textAlign = TextAlign.Start,
                    color = JypColors.Text80,
            )
        }



        Spacer(modifier = Modifier.size(12.dp))
        Divider()
        Spacer(modifier = Modifier.size(12.dp))

        Text(
                modifier = Modifier.padding(start = 14.dp),
                text = "로그아웃",
                type = TextType.TITLE_6,
                textAlign = TextAlign.Start,
                color = JypColors.Text80,
        )
    }
}

@Composable
internal fun Divider() {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0x19000000))
    )
}

@Composable
@Preview
internal fun MyPageScreenPreview() {
    MyPageScreen(
            journeyPropensity = "자유로운 탐험가"
    )
}
