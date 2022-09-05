package com.jyp.feature_question

import androidx.lifecycle.*


class QuestionViewModel: ViewModel() {

    private var _selectedQuestionOptions = MutableLiveData<List<Int>>()
    val selectedQuestionOptions: LiveData<List<Int>> get() = _selectedQuestionOptions

    private val updatedSelectedQuestionOptions = mutableListOf<Int>()



    fun setSelectedQuestionOptions(index: Int, selectedOption: Int) {
        updatedSelectedQuestionOptions.clear()
        _selectedQuestionOptions.value?.forEach { updatedSelectedQuestionOptions.add(it) }

        updatedSelectedQuestionOptions.add(index, selectedOption)
        _selectedQuestionOptions.value = updatedSelectedQuestionOptions
    }
}