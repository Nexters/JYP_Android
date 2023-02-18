package com.jyp.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.main.domain.GetMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
) : ViewModel() {
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String>
        get() = _userName

    private val _personality = MutableStateFlow("")
    val personality: StateFlow<String>
        get() = _personality

    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition

    fun fetchUser() {
        viewModelScope.launch {
            getMeUseCase.invoke()
                .onSuccess { user ->
                    _userName.value = user.name
                    _personality.value = user.personality
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }
}
