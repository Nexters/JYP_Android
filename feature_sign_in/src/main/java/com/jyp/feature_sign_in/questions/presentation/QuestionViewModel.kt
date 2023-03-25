package com.jyp.feature_sign_in.questions.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jyp.feature_sign_in.questions.QuestionResultEnum
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

    fun getSelectedQuestionOptionsAsEnum(): QuestionResultEnum {
        val selectedQuestionOptionsString =
            _selectedQuestionOptions.toList().joinToString(separator = "")
        return selectedQuestionOptionsString.run {
            when {
                QuestionResultEnum.ME.serialNumbers.contains(this) -> QuestionResultEnum.ME
                QuestionResultEnum.PE.serialNumbers.contains(this) -> QuestionResultEnum.PE
                QuestionResultEnum.RT.serialNumbers.contains(this) -> QuestionResultEnum.RT
                else -> QuestionResultEnum.FW
            }
        }
    }
}
