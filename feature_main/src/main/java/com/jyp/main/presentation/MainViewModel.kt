package com.jyp.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.enumerate.PersonalityId
import com.jyp.core_network.jyp.request.CreateUserRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val jypApi: JypApi,
) : ViewModel() {
    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }

    init {
        viewModelScope.launch {
            val body = CreateUserRequestBody(
                    "kakao",
                    "556894",
                    "아무개",
                    "https://journeypiki.duckdns.org/static/profile_me.png",
                    PersonalityId.ME,
            )

            val response = jypApi.createUser(body)
            println(response)
        }
    }
}
