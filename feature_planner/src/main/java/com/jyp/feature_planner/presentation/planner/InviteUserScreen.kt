package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun InviteUserScreen(
    onClickBackButton: () -> Unit,
    onClickCopyInvitationLinkButton: () -> Unit
) {
    GlobalNavigationBarLayout(
        color = GlobalNavigationBarColor.WHITE,
        title = "",
        titleSize = 16.sp,
        titleFontWeight = FontWeight.Medium,
        activeBack = true,
        backAction = { onClickBackButton() }
    ) {
        Column(
            modifier = it
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 8.dp
                )
        ) {
            JypText(
                text = "일행을 초대해 주세요",
                type = TextType.TITLE_1,
                color = JypColors.Text80
            )
            Spacer(modifier = Modifier.height(6.dp))
            JypText(
                text = "일행은 최대 8명까지 초대할 수 있어요",
                type = TextType.BODY_2,
                color = JypColors.Text40
            )
            Spacer(modifier = Modifier.height(30.dp))
            JypTextButton(
                text = "초대링크 복사",
                buttonType = ButtonType.THICK,
                modifier = Modifier.fillMaxWidth(1f),
                enabled = true,
                buttonColorSet = ButtonColorSetType.PINK,
                onClickEnabled = { onClickCopyInvitationLinkButton() }
            )
        }
    }
}

@Preview
@Composable
private fun InviteUserScreenPreview() {
    InviteUserScreen(
        onClickBackButton = {},
        onClickCopyInvitationLinkButton = {}
    )
}