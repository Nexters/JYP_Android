package com.jyp.feature_sign_in.questions.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_sign_in.questions.QuestionResultEnum
import com.jyp.feature_sign_in.questions.domain.QuestionUseCase
import com.jyp.feature_sign_in.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionUseCase: QuestionUseCase
) : ViewModel() {

    private val _selectedQuestionOptions = mutableStateListOf<Int?>(null, null, null)
    val selectedQuestionOptions: List<Int?> get() = _selectedQuestionOptions

    private val _createUserAccountUiState = MutableStateFlow<UiState>(UiState.Loading)
    val createUserAccountUiState: StateFlow<UiState>
        get() = _createUserAccountUiState


    fun setSelectedQuestionOptions(index: Int, selectedOption: Int) {
        if (_selectedQuestionOptions[index] == selectedOption) return
        _selectedQuestionOptions[index] = selectedOption
    }

    fun getSelectedQuestionOptionsAsEnum(): QuestionResultEnum {
        Log.d("TAG", "getSelectedQuestionOptionsAsEnum: $_selectedQuestionOptions")
        Log.d("TAG", "getSelectedQuestionOptionsAsEnum: ${_selectedQuestionOptions.toList()}")
        val selectedQuestionOptionsString = _selectedQuestionOptions.toList().joinToString(separator = "")
        Log.d("TAG", "getSelectedQuestionOptionsAsEnum: $selectedQuestionOptionsString")
        return selectedQuestionOptionsString.run { // Todo: Check if list.toString() is equals with append().
            when {
                QuestionResultEnum.ME.serialNumbers.contains(this) -> QuestionResultEnum.ME
                QuestionResultEnum.PE.serialNumbers.contains(this) -> QuestionResultEnum.PE
                QuestionResultEnum.RT.serialNumbers.contains(this) -> QuestionResultEnum.RT
                else -> QuestionResultEnum.FW
            }
        }
    }

    fun createUserAccount(
        body: CreateUserRequestBody
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            questionUseCase.createUserAccount(body)
                .onSuccess { user ->
                    _createUserAccountUiState.value = UiState.Success(user)
                }
                .onFailure { throwable ->
                    throwable.localizedMessage?.let {
                        _createUserAccountUiState.value = UiState.Failure(it)
                    }
                }
        }
    }
}
