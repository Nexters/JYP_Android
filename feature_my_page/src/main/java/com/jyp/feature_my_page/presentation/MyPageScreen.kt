package com.jyp.feature_my_page.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.Avatar
import com.jyp.jyp_design.ui.avatar.AvatarType
import com.jyp.jyp_design.ui.text.*
import com.jyp.jyp_design.ui.typography.type.*

@Composable
fun MyPageScreen(
    profileImagePath: String,
    personality: String,
    userName: String,
    onClickAppInfo: () -> Unit,
    onClickSignOut: () -> Unit,
    onClickWithdrawal: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white200)
            .verticalScroll(scrollState)
    ) {
        Profile(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(JypColors.Background_white100),
            profileImagePath = profileImagePath,
            personality = personality,
            userName = userName
        )
        Menu(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    horizontal = 24.dp,
                    vertical = 28.dp
                ),
            onClickAppInfo = onClickAppInfo,
            onClickSignOut = onClickSignOut,
            onClickWithdrawal = onClickWithdrawal
        )
    }
}

@Composable
private fun Profile(
    modifier: Modifier,
    profileImagePath: String,
    personality: String,
    userName: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Avatar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            profileImageUrl = profileImagePath,
            avatarType = AvatarType.LARGE
        )
        Spacer(modifier = Modifier.size(12.dp))
        JypText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = personality,
            type = TextType.HEADING_2,
            textAlign = TextAlign.Center,
            color = JypColors.Text90,
        )
        Spacer(modifier = Modifier.size(2.dp))
        JypText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = userName,
            type = TextType.BODY_1,
            textAlign = TextAlign.Center,
            color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(30.dp))
    }
}

@Composable
internal fun Menu(
    modifier: Modifier,
    onClickAppInfo: () -> Unit,
    onClickSignOut: () -> Unit,
    onClickWithdrawal: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
    ) {
        JypText(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 23.dp
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { onClickAppInfo() },
            text = "앱 소식 및 설명서",
            type = TextType.TITLE_5,
            textAlign = TextAlign.Start,
            color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 23.dp
                )
        ) {
            JypText(
                modifier = Modifier.weight(1f),
                text = "버전 정보",
                type = TextType.TITLE_5,
                textAlign = TextAlign.Start,
                color = JypColors.Text80,
            )
            JypText(
                text = "1.0",
                type = TextType.BODY_1,
                textAlign = TextAlign.End,
                color = JypColors.Text40,
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        JypText(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 23.dp
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { onClickSignOut() },
            text = "로그아웃",
            type = TextType.TITLE_5,
            textAlign = TextAlign.Start,
            color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(12.dp))
        JypText(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 23.dp
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { onClickWithdrawal() },
            text = "회원 탈퇴",
            type = TextType.TITLE_5,
            textAlign = TextAlign.Start,
            color = JypColors.Text80,
        )
    }
}

@Composable
@Preview
internal fun MyPageScreenPreview() {
    MyPageScreen(
        profileImagePath = "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
        personality = "자유로운 탐험가",
        userName = "박진영",
        onClickAppInfo = {},
        onClickSignOut = {},
        onClickWithdrawal = {}
    )
}
