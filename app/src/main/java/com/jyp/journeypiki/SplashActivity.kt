package com.jyp.journeypiki

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import com.jyp.feature_planner.presentation.planner.PlannerActivity
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }

        Handler(Looper.getMainLooper()).postDelayed(500L) {
            // TODO : onboarding 화면으로 이동하기
            startActivity(Intent(this, PlannerActivity::class.java))
        }
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
