package com.jyp.feature_my_page.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun ConfirmWithdrawalBottomSheetScreen(
    onClickCancelButton: () -> Unit,
    onClickWithdrawalButton: () -> Unit
) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 28.dp)
    ) {
        JypText(
                text = "정말 떠나시는 건가요?",
                type = TextType.TITLE_2,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = "회원 탈퇴시 더 이상 해당 계정으로\n로그인할 수 없습니다",
                type = TextType.BODY_2,
                color = JypColors.Text75,
        )
        Spacer(modifier = Modifier.size(72.dp))
        Row(
                modifier = Modifier.fillMaxWidth(),
        ) {
            JypTextButton(
                    modifier = Modifier.weight(1f),
                    text = "아니요",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.GRAY,
                    enabled = true,
                    onClickEnabled = onClickCancelButton
            )
            Spacer(modifier = Modifier.size(13.dp))
            JypTextButton(
                    modifier = Modifier.weight(1f),
                    text = "떠날게요",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.PINK,
                    enabled = true,
                    onClickEnabled = onClickWithdrawalButton
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ConfirmWithdrawalBottomSheetScreenPreview() {
    ConfirmWithdrawalBottomSheetScreen(
        onClickCancelButton = {},
        onClickWithdrawalButton = {}
    )
}
