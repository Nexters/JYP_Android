package com.jyp.feature_sign_in.questions.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.jyp.core_network.jyp.TOKEN
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.feature_sign_in.R
import com.jyp.feature_sign_in.util.UiState
import com.jyp.feature_sign_in.util.setIntentTo
import com.jyp.feature_sign_in.util.showToast
import com.jyp.main.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels()
    private val token: String? by lazy {
        intent.getStringExtra(TOKEN)
    }
    private val userName: String? by lazy {
        intent.getStringExtra(USER_NAME)
    }
    private val profileImagePath: String? by lazy {
        intent.getStringExtra(PROFILE_IMAGE_PATH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                viewModel = viewModel,
                onClickDoneQuestion = {
                    if (token.isNullOrBlank()) showToast(R.string.sign_in_error)
                    if (userName.isNullOrBlank()) showToast(R.string.sign_in_error)
                    if (profileImagePath.isNullOrBlank()) showToast(R.string.sign_in_error)

                    viewModel.createUserAccount(
                        token!!,
                        CreateUserRequestBody(
                            name = userName!!,
                            profileImagePath = profileImagePath!!,
                            personalityId = viewModel.getSelectedQuestionOptionsAsEnum().name
                        )
                    )
                }
            )
        }

        initCreateUserAccountUiStateCollector()
    }

    private fun initCreateUserAccountUiStateCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.createUserAccountUiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        val token = uiState.result as String
                        setIntentTo(MainActivity::class.java) {
                            putString(TOKEN, token)
                        }
                    }
                    is UiState.Failure -> showToast(uiState.message)
                }
            }
        }
    }

    companion object {
        const val USER_NAME = "USER_NAME"
        const val PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH"
    }
}

@Composable
private fun Screen(
    viewModel: QuestionViewModel,
    onClickDoneQuestion: () -> Unit
) {
    val selectedQuestionOptions = remember { viewModel.selectedQuestionOptions }

    QuestionScreen(
        selectedQuestionOptions = selectedQuestionOptions,
        onClickQuestionOption = viewModel::setSelectedQuestionOptions,
        onClickDoneQuestion = onClickDoneQuestion
    )
}
