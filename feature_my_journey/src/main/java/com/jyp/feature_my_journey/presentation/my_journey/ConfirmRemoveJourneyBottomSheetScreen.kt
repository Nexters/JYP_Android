package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.jyp_design.enumerate.ThemeType
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun ConfirmRemoveJourneyBottomSheetScreen(
        journey: Journey,
) {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 28.dp)
    ) {
        JypText(
                text = "${journey.title}를\n정말 삭제하시나요?",
                type = TextType.TITLE_2,
                color = JypColors.Text80,
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = "삭제한 플래너는 다시 확인할 수 없어요",
                type = TextType.BODY_2,
                color = JypColors.Text75,
        )
        Spacer(modifier = Modifier.size(72.dp))
        Row(
                modifier = Modifier.fillMaxWidth(),
        ) {
            JypTextButton(
                    modifier = Modifier.weight(1f),
                    text = "싫어요",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.GRAY,
                    enabled = true,
            )
            Spacer(modifier = Modifier.size(13.dp))
            JypTextButton(
                    modifier = Modifier.weight(1f),
                    text = "좋아요",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.PINK,
                    enabled = true,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ConfirmRemoveJourneyBottomSheetScreenPreview() {
    ConfirmRemoveJourneyBottomSheetScreen(Journey("", "", "qwdqwd", ThemeType.DEFAULT, "", "", emptyList()))
}
