package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun PlannerJourneyPlanScreen(

) {
    PlanGroupItem()
}

@Composable
private fun PlanGroupItem() {
    Row(
            modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .border(
                            width = 1.dp,
                            color = JypColors.Black10,
                            shape = RoundedCornerShape(size = 12.dp),
                    )
                    .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            JypText(
                    text = "Day 1",
                    type = TextType.TITLE_6,
                    color = JypColors.Text80,
            )
            Spacer(modifier = Modifier.size(14.dp))
            JypText(
                    text = "7월 18일",
                    type = TextType.BODY_1,
                    color = JypColors.Text40,
            )
        }

        Image(
                modifier = Modifier.size(24.dp),
                painter = JypPainter.add,
                contentDescription = null,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PlannerJourneyPlanScreenPreview() {
    PlannerJourneyPlanScreen(

    )
}
