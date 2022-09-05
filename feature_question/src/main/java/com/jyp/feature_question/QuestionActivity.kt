package com.jyp.feature_question

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(viewModel)
        }
    }
}

@Composable
private fun Screen(viewModel: QuestionViewModel) {
    QuestionScreen(viewModel = viewModel)
}