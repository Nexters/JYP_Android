package com.jyp.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.feature_my_journey.presentation.my_journey.*
import com.jyp.feature_my_page.presentation.MyPageScreen
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.EXTRA_CREATE_PLANNER_STEP
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.JOIN_PLANNER_ERROR_BODY
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.JOIN_PLANNER_ERROR_TITLE
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.RESULT_CODE_JOIN_PLANNER_FAILURE
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_PLANNER_ID
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val myJourneyViewModel: MyJourneyViewModel by viewModels()

    private lateinit var joinJourneyResult: ActivityResultLauncher<Intent>

    private lateinit var joinPlannerErrorTitle: String
    private lateinit var joinPlannerErrorBody: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinJourneyResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != RESULT_CODE_JOIN_PLANNER_FAILURE) {
                result.data?.let {
                    joinPlannerErrorTitle = it.getStringExtra(JOIN_PLANNER_ERROR_TITLE) ?: ""
                    joinPlannerErrorBody = it.getStringExtra(JOIN_PLANNER_ERROR_BODY) ?: ""
                }
            }
        }

        setContent {
            Screen(
                mainViewModel = mainViewModel,
                myJourneyViewModel = myJourneyViewModel,
                onClickCreateJourney = {
                    startActivity(Intent(this, CreatePlannerActivity::class.java))
                },
                onClickJoinJourneyNextButton = { plannerId ->
                    joinJourneyResult.launch(
                        Intent(this, CreatePlannerActivity::class.java).apply {
                            putExtra(EXTRA_PLANNER_ID, plannerId)
                            putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE)
                        }
                    )
                },
                onClickPlanner = { plannerId ->
                    startActivity(
                        Intent(this, PlannerActivity::class.java).apply {
                            putExtra(EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
                joinPlannerFailureTitle = joinPlannerErrorTitle,
                joinPlannerFailureBody = joinPlannerErrorBody
            )
        }

        mainViewModel.fetchUser()
    }

    override fun onResume() {
        super.onResume()
        myJourneyViewModel.fetchJourneyList()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    mainViewModel: MainViewModel,
    myJourneyViewModel: MyJourneyViewModel,
    onClickCreateJourney: () -> Unit,
    onClickJoinJourneyNextButton: (String) -> Unit,
    onClickPlanner: (id: String) -> Unit,
    joinPlannerFailureTitle: String,
    joinPlannerFailureBody: String
) {
    val coroutineScope = rememberCoroutineScope()
    var currentBottomSheetItem by remember {
        mutableStateOf<MainBottomSheetItem>(
            MainBottomSheetItem.None
        )
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val myJourneyScreenItem = createMyJourneyScreenItem(
        mainViewModel = mainViewModel,
        myJourneyViewModel = myJourneyViewModel,
        onClickNewJourney = {
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.NewJourney
                modalBottomSheetState.show()
            }
        },
        onClickPlanner = { journey ->
            onClickPlanner.invoke(journey.id)
        },
        onClickMore = { journey ->
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.JourneyMore(journey)
                modalBottomSheetState.show()
            }
        }
    )

    val anotherJourneyScreenItem = createAnotherJourneyScreenItem()
    val myPageScreenItem = createMyPageScreenItem()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            when (val bottomSheetItem = currentBottomSheetItem) {
                is MainBottomSheetItem.None -> {
                    Box(
                        modifier = Modifier
                            .background(JypColors.Background_grey300)
                            .size(1.dp)
                    )
                }
                is MainBottomSheetItem.NewJourney -> {
                    NewJourneyBottomSheetScreen(
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        },
                        onClickCreateJourney = {
                            onClickCreateJourney()
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        },
                        onClickJoinJourney = {
                            coroutineScope.launch {
                                currentBottomSheetItem = MainBottomSheetItem.JoinJourney
                                modalBottomSheetState.show()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.JoinJourney -> {
                    JoinJourneyBottomSheetScreen(
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        },
                        onClickNextButton = { plannerId ->
                            onClickJoinJourneyNextButton(plannerId)
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.FailToJoinJourney -> {
                    if (joinPlannerFailureTitle.isBlank()) return@ModalBottomSheetLayout
                    if (joinPlannerFailureBody.isBlank()) return@ModalBottomSheetLayout
                    FailToJoinJourneyBottomSheetScreen(
                        failToJoinJourneyTitle = joinPlannerFailureTitle,
                        failToJoinJourneyBody = joinPlannerFailureBody,
                        onClickConfirmButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.JourneyMore -> {
                    JourneyMoreBottomSheetScreen(
                        journey = bottomSheetItem.journey,
                        onClickRemove = { journey ->
                            currentBottomSheetItem = MainBottomSheetItem.ConfirmRemoveJourney(journey)
                        },
                    )
                }
                is MainBottomSheetItem.ConfirmRemoveJourney -> {
                    ConfirmRemoveJourneyBottomSheetScreen(
                        journey = bottomSheetItem.journey,
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        },
                        onClickLeaveJourney = {
                            myJourneyViewModel.leaveJourney(bottomSheetItem.journey.id)
                        }
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        MainScreen(
            listOf(
                myJourneyScreenItem,
                anotherJourneyScreenItem,
                myPageScreenItem,
            )
        )
    }

    SelectProfileScreen(mainViewModel)
}

@Composable
private fun SelectProfileScreen(mainViewModel: MainViewModel) {
    val selectedPosition by mainViewModel.profileSelectedPosition.collectAsState()
    val userName by mainViewModel.userName.collectAsState()
    val profileImagePath by mainViewModel.profileImagePath.collectAsState()
    val personality by mainViewModel.personality.collectAsState()
    val personalityImagePath by mainViewModel.personalityImagePath.collectAsState()

    var isShow by remember {
        mutableStateOf(true)
    }

    AnimatedVisibility(
        visible = isShow,
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = keyframes {
                durationMillis = 400
            }
        ),
    ) {
        SelectProfileScreen(
            name = userName,
            profileImagePath = profileImagePath,
            personality = personality,
            personalityImagePath = personalityImagePath,
            selectedPosition = selectedPosition,
            showDim = isShow,
            onSelectProfile = mainViewModel::selectProfile,
            submitProfile = {
                isShow = false
                mainViewModel.updateSelectedProfile()
            }
        )
    }
}

private fun createMyJourneyScreenItem(
    mainViewModel: MainViewModel,
    myJourneyViewModel: MyJourneyViewModel,
    onClickNewJourney: () -> Unit,
    onClickPlanner: (Journey) -> Unit,
    onClickMore: (Journey) -> Unit,
): MainScreenItem {
    return MainScreenItem(
        navItem = BottomNavItem.MY_JOURNEY,
        content = {
            val userName by mainViewModel.userName.collectAsState("")
            val personality by mainViewModel.personality.collectAsState("")
            val plannedJourneys by myJourneyViewModel.plannedJourneys.collectAsState()
            val pastJourneys by myJourneyViewModel.pastJourneys.collectAsState()

            GlobalNavigationBarLayout(
                color = GlobalNavigationBarColor.GREY,
                title = stringResource(id = BottomNavItem.MY_JOURNEY.labelRes),
                titleSize = 16.sp,
                titleFontWeight = FontWeight.Medium,
            ) {
                MyJourneyScreen(
                    journeyPropensity = personality,
                    userName = userName,
                    plannedJourneys = plannedJourneys,
                    pastJourneys = pastJourneys,
                    onClickNewJourney = onClickNewJourney,
                    onClickPlanner = onClickPlanner,
                    onClickMore = onClickMore,
                )
            }
        }
    )
}

private fun createAnotherJourneyScreenItem(): MainScreenItem {
    return MainScreenItem(
        navItem = BottomNavItem.ANOTHER_JOURNEY,
        content = {
            GlobalNavigationBarLayout(
                color = GlobalNavigationBarColor.WHITE,
                title = stringResource(id = BottomNavItem.ANOTHER_JOURNEY.labelRes),
                titleSize = 16.sp,
                titleFontWeight = FontWeight.Medium,
            ) {
                AnotherJourneyScreen()
            }
        }
    )
}

private fun createMyPageScreenItem(): MainScreenItem {
    return MainScreenItem(
        navItem = BottomNavItem.MY_PAGE,
        content = {
            GlobalNavigationBarLayout(
                color = GlobalNavigationBarColor.GREY,
                title = stringResource(id = BottomNavItem.MY_PAGE.labelRes),
                titleSize = 16.sp,
                titleFontWeight = FontWeight.Medium,
            ) {
                MyPageScreen(
                    journeyPropensity = "자유로운 탐험가",
                )
            }
        }
    )
}
