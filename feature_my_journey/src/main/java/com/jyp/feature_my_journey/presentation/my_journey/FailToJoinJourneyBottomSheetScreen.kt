package com.jyp.feature_my_journey.presentation.my_journey

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
fun FailToJoinJourneyBottomSheetScreen(
    failToJoinJourneyTitle: String,
    failToJoinJourneyBody: String,
    onClickConfirmButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp,
                bottom = 28.dp
            )
    ) {
        JypText(
            text = failToJoinJourneyTitle,
            type = TextType.TITLE_2,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
            text = failToJoinJourneyBody,
            type = TextType.BODY_2,
            color = JypColors.Text75
        )
        Spacer(modifier = Modifier.size(86.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            JypTextButton(
                text = "다음에 함께하기",
                buttonType = ButtonType.THICK,
                modifier = Modifier.weight(1f),
                enabled = true,
                buttonColorSet = ButtonColorSetType.PINK,
                onClickEnabled = { onClickConfirmButton() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FailToJoinJourneyBottomSheetScreenPreview() {
    FailToJoinJourneyBottomSheetScreen(
        failToJoinJourneyTitle = "이미 비행기가 떠났어요!",
        failToJoinJourneyBody = "아쉽지만 다음에 함께해요",
        onClickConfirmButton = {}
    )
}
