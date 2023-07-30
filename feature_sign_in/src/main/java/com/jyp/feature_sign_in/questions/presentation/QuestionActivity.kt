package com.jyp.feature_sign_in.questions.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jyp.core_network.di.JypSessionManager
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_sign_in.R
import com.jyp.feature_sign_in.util.showToast
import com.jyp.main.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class QuestionActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: JypSessionManager
    private val viewModel: QuestionViewModel by viewModels()

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
                    if (userName.isNullOrBlank()) showToast(R.string.sign_in_error)
                    if (profileImagePath.isNullOrBlank()) showToast(R.string.sign_in_error)

                    setIntentTo(
                        it = MainActivity::class.java,
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    ) {
                        putString(MainActivity.EXTRA_USER_NAME, userName)
                        putString(MainActivity.EXTRA_PROFILE_IMAGE_PATH, profileImagePath)
                        putString(MainActivity.EXTRA_PERSONALITY, viewModel.getSelectedQuestionOptionsAsEnum().name)
                    }
                }
            )
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
