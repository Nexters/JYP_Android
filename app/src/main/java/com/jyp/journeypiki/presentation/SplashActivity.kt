package com.jyp.journeypiki.presentation

import android.annotation.SuppressLint
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.jyp.core_network.di.JypSessionManager
import com.jyp.core_network.jyp.model.KakaoSignIn
import com.jyp.feature_sign_in.onboarding.OnboardingActivity
import com.jyp.feature_sign_in.util.UiState
import com.jyp.feature_sign_in.util.setIntentTo
import com.jyp.main.presentation.MainActivity
import com.kakao.sdk.auth.AuthApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalMaterialApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: JypSessionManager
    private val viewModel by viewModels<SplashViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkUserSignInStateWithKakaoToken()
        initSignInUiStateCollector()

        setContent { Screen() }
    }

    private fun checkUserSignInStateWithKakaoToken() {
        val kakaoToken = AuthApiClient.instance.tokenManagerProvider.manager.getToken()
        when (kakaoToken == null) {
            true -> setIntentTo(OnboardingActivity::class.java)
            false -> {
                sessionManager.bearerToken = kakaoToken.accessToken
                viewModel.signInWithKakao()
            }
        }
    }

    private fun initSignInUiStateCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.signInWithKakaoUiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {}
                    is UiState.Success -> (uiState.result as KakaoSignIn).apply {
                        when (kakaoAccount != null) {
                            true -> setIntentTo(OnboardingActivity::class.java)
                            false -> {
                                sessionManager.bearerToken = token
                                setIntentTo(MainActivity::class.java)
                            }
                        }
                    }
                    is UiState.Failure -> setIntentTo(OnboardingActivity::class.java)
                }
            }
        }
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
