package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_my_journey.enum.FailToJoinJourneyEnum
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun FailToJoinJourneyBottomSheetScreen(
    failToJoinJourneyEnum: FailToJoinJourneyEnum,
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
            text = failToJoinJourneyEnum.title,
            type = TextType.TITLE_2,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
            text = failToJoinJourneyEnum.body,
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
        failToJoinJourneyEnum = FailToJoinJourneyEnum.FAIL_WITH_EXPIRED,
        onClickConfirmButton = {}
    )
}
