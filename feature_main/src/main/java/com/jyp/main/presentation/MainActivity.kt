package com.jyp.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.feature_another_journey.presentation.AnotherJourneyScreen
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarLayout
import com.jyp.jyp_design.ui.shadow.drawShadow
import com.jyp.main.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Composable
private fun Screen() {
    MainScreen(
            listOf(
                    MainScreenItem(
                            navItem = BottomNavItem.MY_JOURNEY,
                            content = {
                                Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                ) {
                                    Column(
                                            modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(300.dp)
                                                    .height(400.dp)
                                                    .background(JypColors.Background_white100),
                                    ) {
                                        Text(
                                                modifier = Modifier
                                                        .padding(16.dp)
                                                        .drawShadow(JypColors.Pink),
                                                text = "Hello MY_JOURNEY!"
                                        )
                                        Image(
                                                modifier = Modifier
                                                        .padding(top = 14.dp)
                                                        .drawShadow(JypColors.PinkShadow),
                                                painter = painterResource(R.drawable.ic_bottom_nav_my_journey_active),
                                                contentDescription = null
                                        )

                                    }
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
                                Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = "Hello MY_PAGE!")
                                }
                            }
                    ),
            )
    )
}
