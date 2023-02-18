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

    private val _profileImagePath = MutableStateFlow("")
    val profileImagePath: StateFlow<String>
        get() = _profileImagePath

    private val _personality = MutableStateFlow("")
    val personality: StateFlow<String>
        get() = _personality

    private val _personalityImagePath = MutableStateFlow("")
    val personalityImagePath: StateFlow<String>
        get() = _personalityImagePath

    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition

    fun fetchUser() {
        viewModelScope.launch {
            getMeUseCase.invoke()
                .onSuccess { user ->
                    _userName.value = user.name
                    _profileImagePath.value = user.profileImagePath
                    _personality.value = user.personality
                    _personalityImagePath.value = user.personality.toPersonalityImagePath()
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    private fun String.toPersonalityImagePath(): String {
        return when (this) {
            "꼼꼼한 탐험가" -> "https://journeypiki.duckdns.org/static/profile_me.png"
            "열정왕 탐험가" -> "https://journeypiki.duckdns.org/static/profile_pe.png"
            "낭만적인 여행자" -> "https://journeypiki.duckdns.org/static/profile_rt.png"
            "자유로운 방랑자" -> "https://journeypiki.duckdns.org/static/profile_fw.png"

            else -> ""
        }
    }

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }
}
