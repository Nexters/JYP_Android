package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import com.jyp.feature_my_journey.R
import com.jyp.jyp_design.resource.JypPainter


@Composable
fun NewJourneyBottomSheetScreen(
    onClickCancelButton: () -> Unit,
    onClickCreateJourney: () -> Unit,
    onClickJoinJourney: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp,
                bottom = 34.dp
            )
    ) {
        Header(onClickCancelButton = onClickCancelButton)
        Spacer(modifier = Modifier.size(32.dp))
        SelectOptions(
            options = listOf(
                MyJourneyMoreOption(
                    icon = JypPainter.add,
                    title = "플래너 생성하기",
                    onClickOption = { onClickCreateJourney() }
                ),
                MyJourneyMoreOption(
                    icon = painterResource(R.drawable.icon_key),
                    title = "참여 코드로 플래너 입장하기",
                    onClickOption = { onClickJoinJourney() }
                )
            )
        )
    }
}

@Composable
private fun Header(
    onClickCancelButton: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JypText(
            text = "여행 시작하기",
            type = TextType.TITLE_2,
            color = JypColors.Text80,
        )
        JypText(
            text = "취소",
            type = TextType.BODY_2,
            color = JypColors.Text40,
            modifier = Modifier.clickable { onClickCancelButton() }
        )
    }
}

@Composable
private fun SelectOptions(
    options: List<MyJourneyMoreOption>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .clickable { option.onClickOption.invoke() },
            ) {
                Image(
                    painter = option.icon,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(15.dp))
                JypText(
                    text = option.title,
                    type = TextType.BODY_1,
                    color = JypColors.Text75,
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewJourneyBottomSheetScreenPreview() {
    NewJourneyBottomSheetScreen(
        onClickCancelButton = {},
        onClickCreateJourney = {},
        onClickJoinJourney = {}
    )
}
