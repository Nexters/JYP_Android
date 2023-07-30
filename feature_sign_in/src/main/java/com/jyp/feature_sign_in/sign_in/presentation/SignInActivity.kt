package com.jyp.feature_sign_in.sign_in.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.jyp.core_network.util.TokenManager
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.model.KakaoSignIn
import com.jyp.core_network.util.toJypApiFailure
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_sign_in.questions.presentation.QuestionActivity
import com.jyp.feature_sign_in.R
import com.jyp.feature_sign_in.questions.presentation.QuestionActivity.Companion.PROFILE_IMAGE_PATH
import com.jyp.feature_sign_in.questions.presentation.QuestionActivity.Companion.USER_NAME
import com.jyp.feature_sign_in.util.showToast
import com.jyp.main.presentation.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager
    private val viewModel by viewModels<SignInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickSignInButton = { signInWithKakao() }
            )
        }

        initSignInWithKakaoUiStateCollector()
    }

    private fun signInWithKakao() {
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(
                context = this,
                callback = kakaoSignInCallback
            )
            false -> UserApiClient.instance.loginWithKakaoAccount(
                context = this,
                callback = kakaoSignInCallback
            )
        }
    }

    private val kakaoSignInCallback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, error ->
        oAuthToken?.let { kakaoToken ->
            kakaoToken.idToken?.let { _ ->
                UserApiClient.instance.me { user, error ->
                    error?.let { showToast(R.string.sign_in_error) }
                    user?.kakaoAccount?.profile?.let {
                        tokenManager.setToken(kakaoToken.accessToken)
                        viewModel.signInWithKakao()
                    }
                }
            }
        }
        error?.let {
            when (it is ClientError && it.reason == ClientErrorCause.Cancelled) {
                true -> showToast(R.string.sign_in_canceled)
                false -> showToast(R.string.sign_in_error)
            }
        }
    }

    private fun initSignInWithKakaoUiStateCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.signInWithKakaoUiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {}
                    is UiState.Success<*> -> (uiState.data as KakaoSignIn).let { signIn ->
                        tokenManager.setToken(signIn.token)

                        when (signIn.kakaoAccount == null) {
                            true -> {
                                setIntentTo(
                                    it = MainActivity::class.java,
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP,
                                )
                            }
                            false -> {
                                setIntentTo(QuestionActivity::class.java) {
                                    putString(USER_NAME, signIn.properties.nickname)
                                    putString(PROFILE_IMAGE_PATH, signIn.properties.profileImage)
                                }
                            }
                        }
                    }
                    is UiState.Failure -> {
                        uiState.throwable.printStackTrace()
                        uiState.throwable.toJypApiFailure()?.let {
                            showToast(it.message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Screen(
    onClickSignInButton: () -> Unit
) {
    SignInContent(
        onClickSignInButton = onClickSignInButton
    )
}
