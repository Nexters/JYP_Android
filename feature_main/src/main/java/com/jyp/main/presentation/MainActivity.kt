package com.jyp.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.feature_my_journey.presentation.my_journey.MyJourneyScreen
import com.jyp.feature_my_journey.presentation.my_journey.MyJourneyViewModel
import com.jyp.feature_my_page.presentation.MyPageScreen
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val myJourneyViewModel: MyJourneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    myJourneyViewModel = myJourneyViewModel,
            )
        }
    }
}

@Composable
private fun Screen(
        myJourneyViewModel: MyJourneyViewModel,
) {
    MainScreen(
            listOf(
                    MainScreenItem(
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
                                            onClickNewJourney = myJourneyViewModel::fetchJourneyList,
                                    )
                                }
                            }
                    ),
                    MainScreenItem(
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
                    ),
                    MainScreenItem(
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
                    ),
            )
    )
}
