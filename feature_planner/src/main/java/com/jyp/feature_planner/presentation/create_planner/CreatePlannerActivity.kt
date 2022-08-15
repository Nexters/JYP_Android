package com.jyp.feature_planner.presentation.create_planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Composable
private fun Screen(
        step: CreatePlannerStep = CreatePlannerStep.TITLE,
) {
    GlobalNavigationBarLayout(
            color = GlobalNavigationBarColor.WHITE,
            title = stringResource(id = step.navigationTitleRes),
            titleSize = 16.sp,
            titleFontWeight = FontWeight.Medium,
            activeBack = true,
    ) {
        CreatePlannerScreen(
                step = step,
        )
    }
}
