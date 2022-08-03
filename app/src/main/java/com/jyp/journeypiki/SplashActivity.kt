package com.jyp.journeypiki

import android.annotation.SuppressLint
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }

        Handler(Looper.getMainLooper()).postDelayed(2000L) {
            // TODO : onboarding 화면으로 이동하기
        }
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
