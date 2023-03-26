package com.jyp.journeypiki.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.jyp.core_network.di.JypSessionManager
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.model.KakaoSignIn
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_sign_in.onboarding.OnboardingActivity
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
        setContent { Screen() }
    }

    override fun onResume() {
        super.onResume()
        checkUserSignInStateWithKakaoToken()
        initSignInUiStateCollector()
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
                    is UiState.Success<*> -> (uiState.data as KakaoSignIn).let { signIn ->
                        when (signIn.kakaoAccount != null) {
                            true -> setIntentTo(OnboardingActivity::class.java)
                            false -> {
                                sessionManager.bearerToken = signIn.token

                                setIntentTo(
                                    it = MainActivity::class.java,
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP,
                                )
                            }
                        }

                        finish()
                    }
                    is UiState.Failure -> {
                        setIntentTo(OnboardingActivity::class.java)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
private fun Screen() {
    SplashScreen()
}
