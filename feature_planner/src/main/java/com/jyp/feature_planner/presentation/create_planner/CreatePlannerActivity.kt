package com.jyp.feature_planner.presentation.create_planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlannerActivity : ComponentActivity() {
    private val title: String? by lazy {
        intent.getStringExtra(EXTRA_CREATE_PLANNER_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    step = intent.getSerializableExtra(EXTRA_CREATE_PLANNER_STEP) as? CreatePlannerStep ?: CreatePlannerStep.TITLE,
                    submitOnTitle = { title ->
                        startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.DATE)
                                    putExtra(EXTRA_CREATE_PLANNER_TITLE, title)
                                }
                        )
                    }
            )
        }
    }

    companion object {
        const val EXTRA_CREATE_PLANNER_STEP = "EXTRA_CREATE_PLANNER_STEP"
        const val EXTRA_CREATE_PLANNER_TITLE = "EXTRA_CREATE_PLANNER_TITLE"
        const val EXTRA_CREATE_PLANNER_DATE = "EXTRA_CREATE_PLANNER_DATE"
        const val EXTRA_CREATE_PLANNER_TASTE = "EXTRA_CREATE_PLANNER_TASTE"
    }
}

@Composable
private fun Screen(
        step: CreatePlannerStep = CreatePlannerStep.TASTE,
        submitOnTitle: (String) -> Unit,
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
                submitOnTitle = submitOnTitle,
                submitOnDate = {},
                submitOnTaste = {}
        )
    }
}
