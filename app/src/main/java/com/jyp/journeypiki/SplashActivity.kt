package com.jyp.journeypiki

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import com.jyp.main.presentation.MainActivity
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
//            startActivity(Intent(this, CreatePlannerActivity::class.java).apply {
//                putExtra(CreatePlannerActivity.EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE)
//            })
        }
    }

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, CreatePlannerActivity::class.java).apply {
        })
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
