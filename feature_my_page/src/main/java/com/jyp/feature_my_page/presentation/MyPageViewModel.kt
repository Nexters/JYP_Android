package com.jyp.feature_my_page.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.UiState
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.feature_my_page.domain.WithdrawalUseCase
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val withdrawalUseCase: WithdrawalUseCase
) : ViewModel() {

    private val _signOutWithKakaoUiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val signOutWithKakaoUiState: StateFlow<UiState<*>>
        get() = _signOutWithKakaoUiState

    private val _withdrawAccountUiState = MutableStateFlow<UiState<*>>(UiState.Loading)
    val withdrawAccountUiState: StateFlow<UiState<*>>
        get() = _withdrawAccountUiState


    fun signOut() {
        UserApiClient.instance.logout { error ->
            _signOutWithKakaoUiState.value = when (error == null) {
                true -> UiState.Success(true)
                false -> UiState.Failure(error)
            }
        }
    }

    fun withdrawAccount(
        userId: String
    ) {
        viewModelScope.launch {
            withdrawalUseCase.invoke(userId)
                .onSuccess {
                    UserApiClient.instance.unlink { error ->
                        _withdrawAccountUiState.value = when (error == null) {
                            true -> UiState.Success(it)
                            false -> UiState.Failure(error)
                        }
                    }
                }
                .onFailure {
                    _withdrawAccountUiState.value = UiState.Failure(it)
                }
        }
    }
}