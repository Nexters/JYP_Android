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
fun ConfirmSignOutBottomSheetScreen(
    onClickCancelButton: () -> Unit,
    onClickSignOutButton: () -> Unit
) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 28.dp)
    ) {
        JypText(
                text = "로그아웃 하시나요?",
                type = TextType.TITLE_2,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = "다시 돌아올 때까지 기다릴게요!",
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
                    text = "네",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.PINK,
                    enabled = true,
                    onClickEnabled = onClickSignOutButton
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ConfirmSignOutBottomSheetScreenPreview() {
    ConfirmSignOutBottomSheetScreen(
        onClickCancelButton = {},
        onClickSignOutButton = {}
    )
}
