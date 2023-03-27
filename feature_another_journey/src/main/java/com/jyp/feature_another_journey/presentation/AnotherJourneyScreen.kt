package com.jyp.feature_another_journey.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jyp.feature_another_journey.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun AnotherJourneyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = JypColors.Background_white100)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = JypPainter.anotherJourneyInPrepare,
                contentDescription = null,
                modifier = Modifier.width(100.dp)
            )
            JypText(
                text = stringResource(id = R.string.another_journey_description),
                type = TextType.BODY_2,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center,
                color = JypColors.Text75
            )
        }
    }
}
