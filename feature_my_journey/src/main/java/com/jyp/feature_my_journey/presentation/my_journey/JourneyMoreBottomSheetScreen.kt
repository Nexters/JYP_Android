package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import com.jyp.feature_my_journey.R


@Composable
fun JourneyMoreBottomSheetScreen(
    journey: Journey,
    onClickCancelButton: () -> Unit,
    onClickRemove: (Journey) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp,
                bottom = 54.dp
            )
    ) {
        Header(
            journeyTitle = journey.title,
            onClickCancelButton = onClickCancelButton
        )
        Spacer(modifier = Modifier.size(32.dp))
        SelectOptions(
            options = listOf(
                MyJourneyMoreOption(
                    icon = painterResource(id = R.drawable.ic_delete),
                    title = "나가기",
                    onClickOption = { onClickRemove.invoke(journey) }
                )
            )
        )
    }
}

@Composable
private fun Header(
    journeyTitle: String,
    onClickCancelButton: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        JypText(
            text = journeyTitle,
            type = TextType.TITLE_2,
            color = JypColors.Text80
        )
        JypText(
            text = "취소",
            type = TextType.BODY_2,
            color = JypColors.Text40,
            modifier = Modifier.clickable(
                onClick = onClickCancelButton,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
        )
    }
}

@Composable
private fun SelectOptions(
    options: List<MyJourneyMoreOption>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier.clickable {
                    option.onClickOption.invoke()
                }
            ) {
                Image(
                    painter = option.icon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(15.dp))
                JypText(
                    text = option.title,
                    type = TextType.BODY_1,
                    color = JypColors.Text75
                )
            }
        }
    }
}
