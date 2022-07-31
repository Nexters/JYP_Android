package com.jyp.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.shadow.drawColoredShadow
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
                                                        .drawColoredShadow(JypColors.Pink),
                                                text = "Hello MY_JOURNEY!"
                                        )
                                        Image(
                                                modifier = Modifier
                                                        .padding(top = 14.dp)
                                                        .drawColoredShadow(JypColors.PinkShadow),
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
                                Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = "Hello ANOTHER_JOURNEY!")
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
