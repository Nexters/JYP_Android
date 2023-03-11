package com.jyp.feature_sign_in.sign_in.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_sign_in.sign_in.domain.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _signInWithKakaoUiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val signInWithKakaoUiState: StateFlow<UiState<*>>
        get() = _signInWithKakaoUiState


    fun signInWithKakao() {
        viewModelScope.launch {
            signInUseCase.signInWithKakao()
                .onSuccess { signInInfo ->
                    _signInWithKakaoUiState.value = UiState.Success(signInInfo)
                }
                .onFailure { throwable ->
                    _signInWithKakaoUiState.value = UiState.Failure(throwable)
                }
        }
    }
}