package com.jyp.feature_planner.presentation.add_planner_route

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

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
    AddPlannerRouteScreen()
}
