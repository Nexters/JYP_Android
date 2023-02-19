package com.jyp.feature_planner.presentation.add_planner_route

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlannerRouteActivity : ComponentActivity() {
    private val viewModel: AddPlannerRouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen(
                viewModel = viewModel,
                onClickBackAction = this::finish
            )
        }
    }

    companion object {
        const val EXTRA_JOURNEY_ID = "EXTRA_JOURNEY_ID"
        const val EXTRA_DAY_INDEX = "EXTRA_DAY_INDEX"
        const val EXTRA_PIKMIS = "EXTRA_PIKMIS"
        const val EXTRA_PIKIS = "EXTRA_PIKIS"
    }
}

@Composable
private fun Screen(
    viewModel: AddPlannerRouteViewModel,
    onClickBackAction: () -> Unit,
) {
    val pikmis by viewModel.pikmis.collectAsState()
    val pikis by viewModel.pikis.collectAsState()

    GlobalNavigationBarLayout(
        color = GlobalNavigationBarColor.WHITE,
        title = "Day1",
        titleFontWeight = FontWeight.SemiBold,
        titleSize = 20.sp,
        description = "7월 18일",
        backAction = onClickBackAction,
        activeBack = true,
    ) {
        val activity = LocalContext.current as? Activity

        AddPlannerRouteScreen(
            pikmis = pikmis,
            pikis = pikis,
            onSelectPikme = viewModel::addPiki,
            onRemovePiki = viewModel::removePiki,
            onSubmitPikis = {
                viewModel.setPikis()

                activity?.finish()
            },
        )
    }
}
