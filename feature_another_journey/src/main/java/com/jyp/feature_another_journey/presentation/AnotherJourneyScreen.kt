package com.jyp.feature_another_journey.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_another_journey.R
import com.jyp.jyp_design.resource.JypColors

@Composable
fun AnotherJourneyScreen() {
    Column(
            modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(color = JypColors.Background_white100),
    ) {
        Text(
                modifier = Modifier
                        .padding(top = 8.dp),
                text = stringResource(id = R.string.another_journey_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = JypColors.Text90,
        )
        Text(
                modifier = Modifier
                        .padding(top = 20.dp),
                text = stringResource(id = R.string.another_journey_description),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = JypColors.Text20,
        )
    }
}
