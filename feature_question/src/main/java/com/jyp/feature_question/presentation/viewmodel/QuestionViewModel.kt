package com.jyp.feature_question.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {

    private val _selectedQuestionOptions = mutableStateListOf<Int?>(null, null, null)
    val selectedQuestionOptions: List<Int?> get() = _selectedQuestionOptions


    fun setSelectedQuestionOptions(index: Int, selectedOption: Int) {
        if (_selectedQuestionOptions[index] == selectedOption) return
        _selectedQuestionOptions[index] = selectedOption
    }
}
