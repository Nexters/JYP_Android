package com.jyp.feature_planner.presentation.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlannerActivity : ComponentActivity() {
    private val viewModel: PlannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun Screen(viewModel: PlannerViewModel) {
    val pikMes by viewModel.pikMes.collectAsState()

    PlannerScreen(
            pikMes = pikMes,
    )
}
