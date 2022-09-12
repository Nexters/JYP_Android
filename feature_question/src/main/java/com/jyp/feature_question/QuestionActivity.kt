package com.jyp.feature_question

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
                onQuestionFinished = {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            )
        }
    }
}

@Composable
private fun Screen(
    viewModel: QuestionViewModel,
    onQuestionFinished: () -> Unit
) {
    val selectedQuestionOptions: MutableList<Int?>
            by viewModel.selectedQuestionOptions.observeAsState(mutableListOf())

    QuestionScreen(
        selectedQuestionOptions = selectedQuestionOptions,
        onQuestionOptionSelected = { index: Int, selectedOption: Int ->
            viewModel.setSelectedQuestionOptions(index, selectedOption)
        },
        onQuestionFinished = onQuestionFinished
    )
}
