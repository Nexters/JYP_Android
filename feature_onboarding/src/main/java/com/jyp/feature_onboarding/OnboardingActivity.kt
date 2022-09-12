package com.jyp.feature_onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable


class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onBackPressed = { this.finish() },
                onOnboardingFinished = {
                    startActivity(Intent(this, SignInActivity::class.java))
                }
            )
        }
    }
}

@Composable
private fun Screen(
    onBackPressed: () -> Unit,
    onOnboardingFinished: () -> Unit
) {
    OnboardingContent(
        onBackPressed,
        onOnboardingFinished
    )
}