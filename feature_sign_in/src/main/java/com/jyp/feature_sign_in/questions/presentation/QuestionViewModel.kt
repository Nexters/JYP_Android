package com.jyp.feature_sign_in.questions.presentation

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
        val selectedQuestionOptionsString = _selectedQuestionOptions.toList().joinToString(separator = "")
        return selectedQuestionOptionsString.run {
            when {
                QuestionResultEnum.ME.serialNumbers.contains(this) -> QuestionResultEnum.ME
                QuestionResultEnum.PE.serialNumbers.contains(this) -> QuestionResultEnum.PE
                QuestionResultEnum.RT.serialNumbers.contains(this) -> QuestionResultEnum.RT
                else -> QuestionResultEnum.FW
            }
        }
    }

    fun createUserAccount(
        token: String,
        body: CreateUserRequestBody
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            questionUseCase.createUserAccount(token, body)
                .onSuccess {
                    _createUserAccountUiState.value = UiState.Success(token)
                }
                .onFailure { throwable ->
                    throwable.localizedMessage?.let {
                        _createUserAccountUiState.value = UiState.Failure(it)
                    }
                }
        }
    }
}