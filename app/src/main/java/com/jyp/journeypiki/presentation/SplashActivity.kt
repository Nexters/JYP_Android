package com.jyp.journeypiki.presentation

import android.annotation.SuppressLint
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.jyp.feature_sign_in.onboarding.OnboardingActivity
import com.jyp.feature_sign_in.util.MySharedPreferences
import com.jyp.feature_sign_in.util.setIntentTo
import com.jyp.feature_sign_in.util.showToast
import com.jyp.main.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterialApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkUserSignInState()
        setContent { Screen() }
    }

    private fun checkUserSignInState() {
        when (
            MySharedPreferences.getUserId != "" &&
            MySharedPreferences.getAccessToken != ""
        ) {
            true -> setIntentTo(MainActivity())
            false -> setIntentTo(OnboardingActivity())
        }
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
