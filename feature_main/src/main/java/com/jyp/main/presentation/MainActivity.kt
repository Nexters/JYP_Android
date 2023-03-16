package com.jyp.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.core_network.di.JypSessionManager
import com.jyp.core_network.jyp.UiState
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.feature_my_journey.presentation.my_journey.*
import com.jyp.feature_my_page.presentation.ConfirmSignOutBottomSheetScreen
import com.jyp.feature_my_page.presentation.ConfirmWithdrawalBottomSheetScreen
import com.jyp.feature_my_page.presentation.MyPageScreen
import com.jyp.feature_my_page.presentation.MyPageViewModel
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity
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
                onClickPlanner = { plannerId ->
                    startActivity(
                        Intent(this, PlannerActivity::class.java).apply {
                            putExtra(PlannerActivity.EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    mainViewModel: MainViewModel,
    myJourneyViewModel: MyJourneyViewModel,
    myPageViewModel: MyPageViewModel,
    onClickCreateJourney: () -> Unit,
    onClickPlanner: (id: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var currentBottomSheetItem by remember {
        mutableStateOf<MainBottomSheetItem>(MainBottomSheetItem.None)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
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
    val myPageScreenItem = createMyPageScreenItem(
        mainViewModel = mainViewModel,
        myPageViewModel = myPageViewModel,
        onClickSignOut = {
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.ConfirmSignOut
                modalBottomSheetState.show()
            }
        },
        onClickWithdrawal = {
            coroutineScope.launch {
                currentBottomSheetItem = MainBottomSheetItem.ConfirmWithdrawal(mainViewModel.userId.value)
                modalBottomSheetState.show()
            }
        }
    )

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
                            onClickCreateJourney = onClickCreateJourney,
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

private fun createMyPageScreenItem(
    mainViewModel: MainViewModel,
    myPageViewModel: MyPageViewModel,
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
                        onClickAppInfo = {},
                        onClickSignOut = onClickSignOut,
                        onClickWithdrawal = onClickWithdrawal
                    )
                }
            }
    )
}
