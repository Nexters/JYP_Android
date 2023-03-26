package com.jyp.main.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.model.User
import com.jyp.core_network.jyp.onFailure
import com.jyp.core_network.jyp.onSuccess
import com.jyp.main.domain.CreateUserUseCase
import com.jyp.main.domain.GetMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMeUseCase: GetMeUseCase,
    private val createUserUseCase: CreateUserUseCase,
) : ViewModel() {
    private val signUpTempUserName = savedStateHandle[MainActivity.EXTRA_USER_NAME] ?: ""
    private val signUpTempProfileImagePath =
        savedStateHandle[MainActivity.EXTRA_PROFILE_IMAGE_PATH] ?: ""
    private val signUpTempPersonality = savedStateHandle[MainActivity.EXTRA_PERSONALITY] ?: ""

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String>
        get() = _userId

    private val _userName = MutableStateFlow(signUpTempUserName)
    val userName: StateFlow<String>
        get() = _userName

    private val _profileImagePath = MutableStateFlow(signUpTempProfileImagePath)
    val profileImagePath: StateFlow<String>
        get() = _profileImagePath

    private val _personality = MutableStateFlow(signUpTempPersonality.toPersonalityName())
    val personality: StateFlow<String>
        get() = _personality

    private val _personalityImagePath =
        MutableStateFlow(signUpTempPersonality.toPersonalityImagePath())
    val personalityImagePath: StateFlow<String>
        get() = _personalityImagePath

    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition

    private val _isExistMyAccount = MutableStateFlow(signUpTempUserName.isEmpty())
    val isExistMyAccount: StateFlow<Boolean>
        get() = _isExistMyAccount

    fun fetchUser() {
        viewModelScope.launch {
            getMeUseCase.invoke()
                .onSuccess { user ->
                    applyUserData(user)
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                }
        }
    }

    private fun String.toPersonalityImagePath(): String {
        return when (this) {
            "ME" -> "https://journeypiki.duckdns.org/static/profile_me.png"
            "PE" -> "https://journeypiki.duckdns.org/static/profile_pe.png"
            "RT" -> "https://journeypiki.duckdns.org/static/profile_rt.png"
            "FW" -> "https://journeypiki.duckdns.org/static/profile_fw.png"

            else -> ""
        }
    }

    private fun String.toPersonalityName(): String {
        return when (this) {
            "ME" -> "꼼꼼한 탐험가"
            "PE" -> "열정왕 탐험가"
            "RT" -> "낭만적인 여행자"
            "FW" -> "자유로운 방랑자"

            else -> ""
        }
    }

    private fun applyUserData(user: User) {
        _userId.value = user.id
        _userName.value = user.name
        _profileImagePath.value = user.profileImagePath
        _personality.value = user.personality
        _personalityImagePath.value = user.personality.toPersonalityImagePath()
    }

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }

    fun createUser() {
        viewModelScope.launch {
            val profileImagePath = when (profileSelectedPosition.value) {
                0 -> profileImagePath.value
                1 -> personalityImagePath.value
                else -> ""
            }

            createUserUseCase.createUserAccount(
                signUpTempUserName,
                profileImagePath,
                signUpTempPersonality
            ).onSuccess { user ->
                _isExistMyAccount.value = true

                applyUserData(user)
            }.onFailure { throwable ->
                throwable.printStackTrace()
            }
        }
    }
}
