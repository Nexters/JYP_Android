package com.jyp.feature_planner.presentation.create_planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.feature_planner.domain.UiState
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.presentation.create_planner.model.*
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_PLANNER_ID
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

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateFlowCollector()
        setContent {
            Screen(
                viewModel = viewModel,
                step = intent.getSerializableExtra(EXTRA_CREATE_PLANNER_STEP) as? CreatePlannerStep
                    ?: CreatePlannerStep.TITLE,
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
                            putExtra(EXTRA_CREATE_PLANNER_THEME_TYPE, themeType)
                            putExtra(EXTRA_CREATE_PLANNER_DATE, startMillis to endMillis)
                        }
                    )
                },
                submitOnTaste = { tags ->
                    when (plannerId.isNullOrBlank()) {
                        true -> viewModel.createPlanner(
                            title ?: return@Screen,
                            themeType?.imagePath ?: return@Screen,
                            date?.first ?: return@Screen,
                            date?.second ?: return@Screen,
                            tags,
                        )
                        false -> viewModel.joinPlanner(
                            plannerId ?: "",
                            tags
                        )
                    }
                    finishAffinity()
                }
            )
        }
    }

    private fun initStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.joinPlannerUiState.collect { it ->
                    when (it) {
                        is UiState.Loading -> {}
                        is UiState.Success -> startActivity(
                            Intent(this@CreatePlannerActivity, PlannerActivity::class.java).apply {
                                putExtra(EXTRA_PLANNER_ID, plannerId)
                            }
                        )
                        is UiState.Failure -> { // Todo: Show bottom sheet.
                            Intent().let { intent -> // Todo: Pass data back to main activity.
                                intent.setClassName(this@CreatePlannerActivity.packageName, "com.jyp.main.presentation.MainActivity")
                                intent.putExtra(JOIN_PLANNER_ERROR_TITLE, it.throwable.message)
                                intent.putExtra(JOIN_PLANNER_ERROR_BODY, it.throwable.localizedMessage)
                                setResult(RESULT_OK, intent)
//                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val JOIN_PLANNER_ERROR_TITLE = "JOIN_PLANNER_ERROR_TITLE"
        const val JOIN_PLANNER_ERROR_BODY = "JOIN_PLANNER_ERROR_BODY"

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
    submitOnTaste: (List<PlannerTag>) -> Unit,
) {
    val startDateMillis by viewModel.startDateMillis.collectAsState()
    val endDateMillis by viewModel.endDateMillis.collectAsState()
    val tags by viewModel.tags.collectAsState()

    var createPlannerBottomSheetType by remember {
        mutableStateOf<CreatePlannerBottomSheetType>(CreatePlannerBottomSheetType.SelectTheme)
    }

    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {
            createPlannerBottomSheetType !is CreatePlannerBottomSheetType.AddTag
        }
    )

    val coroutineScope = rememberCoroutineScope()

    var cachedTitle by remember {
        mutableStateOf("")
    }

    BackHandler(enabled = bottomSheetScaffoldState.isVisible) {
        coroutineScope.launch {
            bottomSheetScaffoldState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        sheetContent = {
            when (val type = createPlannerBottomSheetType) {
                is CreatePlannerBottomSheetType.AddTag -> {
                    AddTagBottomSheetScreen(
                        modifier = Modifier
                            .fillMaxHeight(0.9f),
                        tagType = type.tagType,
                        onClickCancel = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.hide()
                            }
                        },
                        onClickSubmit = { tag ->
                            viewModel.addTag(tag)

                            coroutineScope.launch {
                                bottomSheetScaffoldState.hide()
                            }
                        },
                    )
                }
                CreatePlannerBottomSheetType.SelectTheme -> {
                    PlannerThemeSelectBottomSheetScreen { themeType ->
                        submitOnTitle.invoke(cachedTitle, themeType)
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Box {
            GlobalNavigationBarLayout(
                color = GlobalNavigationBarColor.WHITE,
                title = stringResource(id = step.navigationTitleRes),
                titleSize = 16.sp,
                titleFontWeight = FontWeight.Medium,
                activeBack = true,
            ) {
                CreatePlannerScreen(
                    step = step,
                    selectDateClick = selectDateClick,
                    startDateMillis = startDateMillis,
                    endDateMillis = endDateMillis,
                    tags = tags,
                    tagClick = viewModel::clickTag,
                    addTagClick = { tagType ->
                        createPlannerBottomSheetType = CreatePlannerBottomSheetType.AddTag(tagType)

                        coroutineScope.launch {
                            bottomSheetScaffoldState.show()
                        }
                    },
                    submit = { submit ->
                        when (submit) {
                            is CreatePlannerSubmit.Title -> {
                                cachedTitle = submit.title

                                createPlannerBottomSheetType =
                                    CreatePlannerBottomSheetType.SelectTheme

                                coroutineScope.launch {
                                    bottomSheetScaffoldState.show()
                                }
                            }
                            is CreatePlannerSubmit.Date -> {
                                submitOnDate.invoke(submit.from, submit.to)
                            }
                            is CreatePlannerSubmit.Taste -> {
                                submitOnTaste.invoke(submit.tags)
                            }
                        }
                    },
                )
            }
        }
    }
}
