package com.jyp.feature_question

import androidx.lifecycle.*
import kotlinx.coroutines.launch


class QuestionViewModel(
    questionRepositoryImpl: QuestionRepositoryImpl
) : ViewModel() {

    private val _questionDataRepository = MutableLiveData<List<QuestionData>>()
    val questionData: LiveData<List<QuestionData>> get() = _questionDataRepository


    init {
        viewModelScope.launch {
            _questionDataRepository.postValue(questionRepositoryImpl.getQuestion())
        }
    }

    fun updateResponseLiveData(questionIndex: Int, selectedOption: Int) {
        _questionDataRepository.value
    }
}

class QuestionViewModelFactory(
    private val questionRepositoryImpl: QuestionRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionViewModel(questionRepositoryImpl) as T
    }
}