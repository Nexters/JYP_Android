package com.jyp.journeypiki.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_sign_in.util.UiState
import com.jyp.journeypiki.domain.SplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase
) : ViewModel() {

    private val _signInWithKakaoUiState = MutableStateFlow<UiState>(UiState.Loading)
    val signInWithKakaoUiState: StateFlow<UiState>
        get() = _signInWithKakaoUiState


    fun signInWithKakao() {
        viewModelScope.launch {
            splashUseCase.signInWithKakao()
                .onSuccess { signInInfo ->
                    _signInWithKakaoUiState.value = UiState.Success(signInInfo)
                }
                .onFailure { throwable ->
                    throwable.localizedMessage?.let {
                        _signInWithKakaoUiState.value = UiState.Failure(it)
                    }
                }
        }
    }
}