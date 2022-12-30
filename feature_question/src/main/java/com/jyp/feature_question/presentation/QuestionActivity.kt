package com.jyp.feature_question.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jyp.feature_question.presentation.viewmodel.QuestionViewModel
import com.jyp.main.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                viewModel = viewModel,
                onClickDoneQuestion = {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            )
        }
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
