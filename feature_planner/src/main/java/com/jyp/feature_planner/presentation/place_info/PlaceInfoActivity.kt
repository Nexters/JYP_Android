package com.jyp.feature_planner.presentation.place_info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.feature_planner.presentation.planner.PlannerScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaceInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Composable
private fun Screen() {
    PlaceInfoScreen()
}
