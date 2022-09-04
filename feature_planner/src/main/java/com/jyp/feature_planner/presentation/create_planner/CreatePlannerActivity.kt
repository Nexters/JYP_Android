package com.jyp.feature_planner.presentation.create_planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlannerActivity : AppCompatActivity() {
    private val viewModel: CreatePlannerViewModel by viewModels()

    private val title: String? by lazy {
        intent.getStringExtra(EXTRA_CREATE_PLANNER_TITLE)
    }

    private val date: Pair<Long, Long>? by lazy {
        intent.getSerializableExtra(EXTRA_CREATE_PLANNER_DATE) as? Pair<Long, Long>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    viewModel = viewModel,
                    step = intent.getSerializableExtra(EXTRA_CREATE_PLANNER_STEP) as? CreatePlannerStep ?: CreatePlannerStep.TITLE,
                    submitOnTitle = { title ->
                        startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.DATE)
                                    putExtra(EXTRA_CREATE_PLANNER_TITLE, title)
                                }
                        )
                    },
                    selectDateClick = {
                        RangeDatePicker().show(supportFragmentManager) { start, end ->
                            viewModel.updateDate(start, end)
                        }
                    },
                    submitOnDate = { startMillis, endMillis ->
                        startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE)
                                    putExtra(EXTRA_CREATE_PLANNER_TITLE, title)
                                    putExtra(EXTRA_CREATE_PLANNER_DATE, startMillis to endMillis)
                                }
                        )
                    },
                    submitOnTaste = {
                        finishAffinity()
                    }
            )
        }
    }

    companion object {
        const val EXTRA_CREATE_PLANNER_STEP = "EXTRA_CREATE_PLANNER_STEP"
        const val EXTRA_CREATE_PLANNER_TITLE = "EXTRA_CREATE_PLANNER_TITLE"
        const val EXTRA_CREATE_PLANNER_DATE = "EXTRA_CREATE_PLANNER_DATE"
    }
}

@Composable
private fun Screen(
        viewModel: CreatePlannerViewModel,
        step: CreatePlannerStep = CreatePlannerStep.TASTE,
        submitOnTitle: (String) -> Unit,
        selectDateClick: () -> Unit,
        submitOnDate: (Long, Long) -> Unit,
        submitOnTaste: (List<Tag>) -> Unit,
) {
    val startDateMillis by viewModel.startDateMillis.collectAsState()
    val endDateMillis by viewModel.endDateMillis.collectAsState()
    val tags by viewModel.tags.collectAsState()

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
                selectDateClick = selectDateClick,
                startDateMillis = startDateMillis,
                endDateMillis = endDateMillis,
                submitOnDate = submitOnDate,
                tags = tags,
                tagClick = viewModel::clickTag,
                submitOnTaste = submitOnTaste,
        )
    }
}
