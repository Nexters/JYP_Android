package com.jyp.feature_planner.presentation.planner

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import kotlinx.coroutines.*


@Composable
fun InviteUserScreen(
    onClickBackButton: () -> Unit,
    onClickCopyInvitationCodeButton: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isInvitationCodeCopyButtonClicked by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
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
                    text = "참여코드 복사하기",
                    buttonType = ButtonType.THICK,
                    modifier = Modifier.fillMaxWidth(1f),
                    enabled = true,
                    buttonColorSet = ButtonColorSetType.PINK,
                    onClickEnabled = {
                        onClickCopyInvitationCodeButton()
                        if (!isInvitationCodeCopyButtonClicked) {
                            coroutineScope.launch {
                                isInvitationCodeCopyButtonClicked = true
                                delay(1000)
                                isInvitationCodeCopyButtonClicked = false
                            }
                        }
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                isInvitationCodeCopyButtonClicked
            } else {
                // In Android 13 and higher, feedback automatically provided when copying to the clipboard.
                // https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#Feedback
                false
            },
            modifier = Modifier.fillMaxWidth(),
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(600))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopCenter)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                CustomToast(
                    modifier = Modifier
                        .shadow(
                            elevation = 32.dp,
                            shape = RoundedCornerShape(percent = 100),
                            spotColor = JypColors.Border_grey
                        )
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
private fun CustomToast(
    modifier: Modifier
) {
    JypText(
        text = "클립보드에 복사되었어요!",
        type = TextType.TAG_2,
        modifier = modifier
            .background(
                color = JypColors.Background_white100,
                shape = RoundedCornerShape(corner = CornerSize(100))
            )
            .padding(
                horizontal = 58.dp,
                vertical = 16.dp
            ),
        color = JypColors.Text80
    )
}

@Preview
@Composable
private fun InviteUserScreenPreview() {
    InviteUserScreen(
        onClickBackButton = {},
        onClickCopyInvitationCodeButton = {}
    )
}