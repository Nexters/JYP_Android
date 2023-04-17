package com.jyp.feature_sign_in.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_sign_in.sign_in.presentation.SignInActivity


class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onBackPressed = { this.finish() },
                onOnboardingFinished = { setIntentTo(SignInActivity::class.java) }
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