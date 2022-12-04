package com.jyp.feature_planner.presentation.add_planner_route

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout

class AddPlannerRouteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen()
        }
    }
}

@Composable
private fun Screen() {
    GlobalNavigationBarLayout(
            color = GlobalNavigationBarColor.WHITE,
            title = "Day1",
            titleFontWeight = FontWeight.SemiBold,
            titleSize = 20.sp,
            description = "7월 18일",
            backAction = {},
            activeBack = true,
    ) {
        AddPlannerRouteScreen()
    }
}
