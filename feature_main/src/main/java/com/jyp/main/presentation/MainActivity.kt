package com.jyp.main.presentation

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.core_network.jyp.model.enumerate.JoinJourneyFailure
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.core_network.di.JypSessionManager
import com.jyp.core_network.jyp.UiState
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.feature_my_journey.presentation.my_journey.*
import com.jyp.feature_my_page.presentation.*
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.EXTRA_CREATE_PLANNER_ACTION
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.EXTRA_CREATE_PLANNER_STEP
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.JOIN_PLANNER_ERROR_CODE
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity.Companion.RESULT_CODE_JOIN_PLANNER_FAILURE
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerAction
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_IS_D_DAY
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_PLANNER_ID
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: JypSessionManager

    private val mainViewModel: MainViewModel by viewModels()
    private val myJourneyViewModel: MyJourneyViewModel by viewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen(
                mainViewModel = mainViewModel,
                myJourneyViewModel = myJourneyViewModel,
                myPageViewModel = myPageViewModel,
                onClickCreateJourney = {
                    startActivity(Intent(this, CreatePlannerActivity::class.java))
                },
                onClickPlanner = { journey ->
                    startActivity(
                        Intent(this, PlannerActivity::class.java).apply {
                            putExtra(EXTRA_PLANNER_ID, journey.id)
                            putExtra(EXTRA_IS_D_DAY, journey.dDay == "D-day")
                        }
                    )
                },
                onClickAppInfo = {
                    startActivity(
                        Intent(this, AppInfoActivity::class.java)
                    )
                }
            )
        }

        mainViewModel.fetchUser()
        initSignOutStateFlowCollector()
        initWithdrawalStateFlowCollector()
    }

    override fun onResume() {
        super.onResume()
        myJourneyViewModel.fetchJourneyList()
    }

    private fun initSignOutStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myPageViewModel.signOutWithKakaoUiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}
                        is UiState.Success<*> -> resetUserSignInState()
                        is UiState.Failure -> {
                            uiState.throwable.printStackTrace()
                            resetUserSignInState()
                        }
                    }
                }
            }
        }
    }

    private fun initWithdrawalStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myPageViewModel.withdrawAccountUiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}
                        is UiState.Success<*> -> resetUserSignInState()
                        is UiState.Failure -> uiState.throwable.printStackTrace()
                    }
                }
            }
        }
    }

    private fun resetUserSignInState() {
        sessionManager.bearerToken = null
        startActivity(
            Intent().setClassName(
                this@MainActivity,
                "com.jyp.feature_sign_in.onboarding.OnboardingActivity"
            )
        )
    }

    companion object {
        const val EXTRA_USER_NAME = "EXTRA_USER_NAME"
        const val EXTRA_PROFILE_IMAGE_PATH = "EXTRA_PROFILE_IMAGE_PATH"
        const val EXTRA_PERSONALITY = "EXTRA_PERSONALITY"
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    mainViewModel: MainViewModel,
    myJourneyViewModel: MyJourneyViewModel,
    myPageViewModel: MyPageViewModel,
    onClickCreateJourney: () -> Unit,
    onClickPlanner: (journey: Journey) -> Unit,
    onClickAppInfo: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var currentBottomSheetItem by remember {
        mutableStateOf<MainBottomSheetItem>(
            MainBottomSheetItem.None
        )
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            currentBottomSheetItem !is MainBottomSheetItem.JoinJourney
        }
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
            onClickPlanner.invoke(journey)
        },
        onClickMore = { journey ->
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.JourneyMore(journey)
                modalBottomSheetState.show()
            }
        }
    )

    val anotherJourneyScreenItem = createAnotherJourneyScreenItem()

    val myPageScreenItem = createMyPageScreenItem(
        mainViewModel = mainViewModel,
        myPageViewModel = myPageViewModel,
        onClickAppInfo = onClickAppInfo,
        onClickSignOut = {
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.ConfirmSignOut
                modalBottomSheetState.show()
            }
        },
        onClickWithdrawal = {
            coroutineScope.launch {
                currentBottomSheetItem =
                    MainBottomSheetItem.ConfirmWithdrawal(mainViewModel.userId.value)
                modalBottomSheetState.show()
            }
        }
    )

    val context = LocalContext.current
    var joinPlannerErrorCode by remember { mutableStateOf("") }
    val joinJourneyResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != RESULT_CODE_JOIN_PLANNER_FAILURE) return@rememberLauncherForActivityResult
        result.data?.let {
            joinPlannerErrorCode = it.getStringExtra(JOIN_PLANNER_ERROR_CODE) ?: ""
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.FailToJoinJourney
                modalBottomSheetState.show()
            }
        }
    }

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
                            val joinJourneyPendingIntent = PendingIntent.getActivity(
                                context,
                                0,
                                Intent(context, CreatePlannerActivity::class.java)
                                    .putExtra(EXTRA_CREATE_PLANNER_ACTION, CreatePlannerAction.Join(plannerId))
                                    .putExtra(EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE),
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )

                            joinJourneyResultLauncher.launch(
                                IntentSenderRequest.Builder(joinJourneyPendingIntent).build()
                            )
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.FailToJoinJourney -> {
                    if (joinPlannerErrorCode.isBlank()) return@ModalBottomSheetLayout
                    JoinJourneyFailure.getEnumBy(joinPlannerErrorCode)?.let {
                        FailToJoinJourneyBottomSheetScreen(
                            joinJourneyFailure = it,
                            onClickConfirmButton = {
                                coroutineScope.launch {
                                    modalBottomSheetState.hide()
                                }
                            }
                        )
                    }
                }
                is MainBottomSheetItem.ConfirmSignOut -> {
                    ConfirmSignOutBottomSheetScreen(
                        onClickSignOutButton = { myPageViewModel.signOut() },
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.ConfirmWithdrawal -> {
                    ConfirmWithdrawalBottomSheetScreen(
                        onClickWithdrawalButton = {
                            myPageViewModel.withdrawAccount(bottomSheetItem.userId)
                        },
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                is MainBottomSheetItem.JourneyMore -> {
                    JourneyMoreBottomSheetScreen(
                        journey = bottomSheetItem.journey,
                        onClickCancelButton = {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                        },
                        onClickRemove = { journey ->
                            currentBottomSheetItem =
                                MainBottomSheetItem.ConfirmRemoveJourney(journey)
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

    val isExistMyAccount by mainViewModel.isExistMyAccount.collectAsState()

    if (!isExistMyAccount) {
        SelectProfileScreen(mainViewModel)
    }
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
                mainViewModel.createUser()
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

private fun createMyPageScreenItem(
    mainViewModel: MainViewModel,
    myPageViewModel: MyPageViewModel,
    onClickAppInfo: () -> Unit,
    onClickSignOut: () -> Unit,
    onClickWithdrawal: () -> Unit,
): MainScreenItem {

    return MainScreenItem(
        navItem = BottomNavItem.MY_PAGE,
        content = {
            val userName by mainViewModel.userName.collectAsState()
            val personality by mainViewModel.personality.collectAsState()
            val profileImagePath by mainViewModel.profileImagePath.collectAsState()

            GlobalNavigationBarLayout(
                color = GlobalNavigationBarColor.WHITE,
                title = stringResource(id = BottomNavItem.MY_PAGE.labelRes),
                titleSize = 16.sp,
                titleFontWeight = FontWeight.Medium,
            ) {
                MyPageScreen(
                    profileImagePath = profileImagePath,
                    personality = personality,
                    userName = userName,
                    onClickAppInfo = onClickAppInfo,
                    onClickSignOut = onClickSignOut,
                    onClickWithdrawal = onClickWithdrawal
                )
            }
        }
    )
}
