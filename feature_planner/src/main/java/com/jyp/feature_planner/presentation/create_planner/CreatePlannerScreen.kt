package com.jyp.feature_planner.presentation.create_planner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun CreatePlannerScreen(
        step: CreatePlannerStep,
) {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .background(JypColors.Background_white100)
                    .padding(start = 24.dp, top = 12.dp)
    ) {
        CreatePlannerHeader(step = step)
    }
}

@Composable
private fun CreatePlannerHeader(
        step: CreatePlannerStep,
) {
    Column {
        JypText(
                text = stringResource(id = step.titleRes),
                type = TextType.TITLE_1,
                color = JypColors.Text90,
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
                text = stringResource(id = step.descriptionRes),
                type = TextType.BODY_2,
                color = JypColors.Text40,
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun CreatePlannerScreenPreview() {
    CreatePlannerScreen(
            CreatePlannerStep.TITLE,
    )
}
