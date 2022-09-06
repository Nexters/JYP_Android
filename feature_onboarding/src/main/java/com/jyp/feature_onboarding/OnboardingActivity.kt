package com.jyp.feature_onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable


class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(this.finish())
        }
    }
}

@Composable
private fun Screen(finishApp: Unit) {
    OnboardingContent(finishApp)
}