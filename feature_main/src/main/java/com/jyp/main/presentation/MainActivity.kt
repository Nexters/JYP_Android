package com.jyp.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
    private val myJourneyViewModel: MyJourneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen(
                    myJourneyViewModel = myJourneyViewModel,
                    onClickNewJourney = {
                        startActivity(Intent(this, CreatePlannerActivity::class.java))
                    },
                    onClickPlanner = {
                        startActivity(Intent(this, PlannerActivity::class.java))
                    },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
        myJourneyViewModel: MyJourneyViewModel,
        onClickNewJourney: () -> Unit,
        onClickPlanner: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
    )
    var moreClickedJourney by remember {
        mutableStateOf<Journey?>(null)
    }

    val myJourneyScreenItem = createMyJourneyScreenItem(
            myJourneyViewModel = myJourneyViewModel,
            onClickNewJourney = onClickNewJourney,
            onClickPlanner = onClickPlanner,
            onClickMore = { journey ->
                coroutineScope.launch {
                    moreClickedJourney = journey
                    modalBottomSheetState.show()
                }
            }
    )

    val anotherJourneyScreenItem = createAnotherJourneyScreenItem()
    val myPageScreenItem = createMyPageScreenItem()

    ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
                moreClickedJourney.let { journey ->
                    if (journey != null) {
                        JourneyMoreBottomSheetScreen(
                                journey = journey,
                                onClickRemove = {},
                        )
                    } else {
                        Box(
                                modifier = Modifier
                                        .background(JypColors.Background_grey300)
                                        .size(1.dp)
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
}

@OptIn(ExperimentalMaterialApi::class)
private fun createMyJourneyScreenItem(
        myJourneyViewModel: MyJourneyViewModel,
        onClickNewJourney: () -> Unit,
        onClickPlanner: () -> Unit,
        onClickMore: (Journey) -> Unit,
): MainScreenItem {
    return MainScreenItem(
            navItem = BottomNavItem.MY_JOURNEY,
            content = {
                val journeyPropensity = "자유로운 여행가"
                val userName = "박진영"
                val plannedJourneys by myJourneyViewModel.plannedJourneys.collectAsState()
                val pastJourneys by myJourneyViewModel.pastJourneys.collectAsState()

                GlobalNavigationBarLayout(
                        color = GlobalNavigationBarColor.GREY,
                        title = stringResource(id = BottomNavItem.MY_JOURNEY.labelRes),
                        titleSize = 16.sp,
                        titleFontWeight = FontWeight.Medium,
                ) {
                    MyJourneyScreen(
                            journeyPropensity = journeyPropensity,
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
