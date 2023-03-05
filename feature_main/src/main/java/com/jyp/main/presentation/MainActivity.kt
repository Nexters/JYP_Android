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
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.feature_my_journey.domain.Journey
import com.jyp.feature_my_journey.presentation.my_journey.*
import com.jyp.feature_my_page.presentation.MyPageScreen
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val myJourneyViewModel: MyJourneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen(
                mainViewModel = mainViewModel,
                myJourneyViewModel = myJourneyViewModel,
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
