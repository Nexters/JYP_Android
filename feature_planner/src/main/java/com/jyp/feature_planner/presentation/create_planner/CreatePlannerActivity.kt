package com.jyp.feature_planner.presentation.create_planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_planner.domain.Tag
import com.jyp.feature_planner.presentation.planner.TagSelectedBottomSheetScreen
import com.jyp.jyp_design.enumerate.ThemeType
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatePlannerActivity : AppCompatActivity() {
    private val viewModel: CreatePlannerViewModel by viewModels()

    private val title: String? by lazy {
        intent.getStringExtra(EXTRA_CREATE_PLANNER_TITLE)
    }
    
    private val themeType: ThemeType? by lazy {
        intent.getSerializableExtra(EXTRA_CREATE_PLANNER_THEME_TYPE) as? ThemeType
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
                    submitOnTitle = { title, themeType ->
                        startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.DATE)
                                    putExtra(EXTRA_CREATE_PLANNER_TITLE, title)
                                    putExtra(EXTRA_CREATE_PLANNER_THEME_TYPE, themeType)
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
        const val EXTRA_CREATE_PLANNER_THEME_TYPE = "EXTRA_CREATE_PLANNER_THEME_TYPE"
        const val EXTRA_CREATE_PLANNER_DATE = "EXTRA_CREATE_PLANNER_DATE"
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
        viewModel: CreatePlannerViewModel,
        step: CreatePlannerStep = CreatePlannerStep.TASTE,
        submitOnTitle: (String, ThemeType) -> Unit,
        selectDateClick: () -> Unit,
        submitOnDate: (Long, Long) -> Unit,
        submitOnTaste: (List<Tag>) -> Unit,
) {
    val startDateMillis by viewModel.startDateMillis.collectAsState()
    val endDateMillis by viewModel.endDateMillis.collectAsState()
    val tags by viewModel.tags.collectAsState()

    val bottomSheetScaffoldState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()

    var cachedTitle = ""

    ModalBottomSheetLayout(
            sheetState = bottomSheetScaffoldState,
            sheetContent = {
                PlannerThemeSelectBottomSheetScreen { themeType ->
                    submitOnTitle.invoke(cachedTitle, themeType)
                }
            },
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
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
                    submitOnTitle = {
                        cachedTitle = it

                        coroutineScope.launch {
                            bottomSheetScaffoldState.show()
                        }
                    },
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
}
