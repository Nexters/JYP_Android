package com.jyp.feature_planner.presentation.create_planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.util.toJypApiFailure
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.feature_planner.presentation.create_planner.model.*
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_PLANNER_ID
import com.jyp.jyp_design.enumerate.ThemeType
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import com.jyp.jyp_design.ui.typography.type.TextType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatePlannerActivity : AppCompatActivity() {
    private val viewModel: CreatePlannerViewModel by viewModels()

    private val createAction: CreatePlannerAction by lazy {
        intent.getParcelableExtra(EXTRA_CREATE_PLANNER_ACTION)
            ?: CreatePlannerAction.Create() as CreatePlannerAction
    }

    private var rangeDatePicker: RangeDatePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateFlowCollector()

        val action = createAction

        setContent {
            Screen(
                viewModel = viewModel,
                step = intent.getSerializableExtra(EXTRA_CREATE_PLANNER_STEP) as? CreatePlannerStep
                    ?: CreatePlannerStep.TITLE,
                submitOnTitle = { title, themeType ->
                    when (action) {
                        is CreatePlannerAction.Create -> {
                            startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.DATE)

                                    putExtra(
                                        EXTRA_CREATE_PLANNER_ACTION,
                                        action.copy(
                                            title = title,
                                            themeType = themeType,
                                        )
                                    )
                                }
                            )
                        }
                        else -> Unit
                    }
                },
                selectDateClick = {
                    when (rangeDatePicker == null) {
                        true -> rangeDatePicker = RangeDatePicker()
                        false -> rangeDatePicker?.dismiss()
                    }
                    rangeDatePicker?.show(supportFragmentManager) { start, end ->
                        viewModel.updateDate(start, end)
                    }
                },
                submitOnDate = { startMillis, endMillis ->
                    when (action) {
                        is CreatePlannerAction.Create -> {
                            startActivity(
                                Intent(this, CreatePlannerActivity::class.java).apply {
                                    putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE)

                                    putExtra(
                                        EXTRA_CREATE_PLANNER_ACTION,
                                        action.copy(
                                            startDateMillis = startMillis,
                                            endDateMillis = endMillis,
                                        )
                                    )
                                }
                            )
                        }
                        else -> Unit
                    }
                },
                submitOnTaste = { tags ->
                    when (action) {
                        is CreatePlannerAction.Create -> {
                            viewModel.createPlanner(
                                action.title ?: return@Screen,
                                action.themeType?.imagePath ?: return@Screen,
                                action.startDateMillis ?: return@Screen,
                                action.endDateMillis ?: return@Screen,
                                tags,
                            )
                            finishAffinity()
                        }
                        is CreatePlannerAction.Join -> {
                            viewModel.joinPlanner(
                                action.plannerId,
                                tags
                            )
                        }
                    }
                },
                onClickBackAction = this::finish
            )
        }
    }

    override fun onStart() {
        super.onStart()

        val action = createAction

        when (action) {
            is CreatePlannerAction.Create -> null
            is CreatePlannerAction.Join -> action.plannerId
        }?.let {
            viewModel.fetchTags(it)
        }
    }

    private fun initStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.joinPlannerUiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}
                        is UiState.Success -> startActivity(
                            Intent(this@CreatePlannerActivity, PlannerActivity::class.java).apply {
                                putExtra(EXTRA_PLANNER_ID, uiState.data)
                            }
                        )
                        is UiState.Failure -> {
                            uiState.throwable.printStackTrace()
                            uiState.throwable.toJypApiFailure()?.let {
                                setResult(
                                    RESULT_CODE_JOIN_PLANNER_FAILURE,
                                    Intent()
                                        .setClassName(
                                            this@CreatePlannerActivity,
                                            "com.jyp.main.presentation.MainActivity"
                                        )
                                        .putExtra(JOIN_PLANNER_ERROR_CODE, it.code)
                                )
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val RESULT_CODE_JOIN_PLANNER_FAILURE = 1000
        const val JOIN_PLANNER_ERROR_CODE = "JOIN_PLANNER_ERROR_CODE"

        const val EXTRA_CREATE_PLANNER_STEP = "EXTRA_CREATE_PLANNER_STEP"

        const val EXTRA_CREATE_PLANNER_ACTION = "EXTRA_CREATE_PLANNER_ACTION"
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
    onClickBackAction: () -> Unit,
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
                is CreatePlannerBottomSheetType.None -> {
                    Box(
                        modifier = Modifier
                            .background(JypColors.Background_grey300)
                            .size(1.dp)
                    )
                }
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
                textType = TextType.HEADING_3,
                activeBack = true,
                backAction = onClickBackAction,
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
