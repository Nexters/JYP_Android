package com.jyp.feature_question

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {

    private val _selectedQuestionOptions = MutableLiveData<MutableList<Int?>>(mutableListOf(null, null, null))
    val selectedQuestionOptions: LiveData<MutableList<Int?>> get() = _selectedQuestionOptions


    fun setSelectedQuestionOptions(index: Int, selectedOption: Int) {
        val tempSelectedQuestionOptions = _selectedQuestionOptions.value
        tempSelectedQuestionOptions?.set(index, selectedOption)
        _selectedQuestionOptions.value = tempSelectedQuestionOptions
    }
}
