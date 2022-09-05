package com.jyp.main.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _profileSelectedPosition = MutableStateFlow<Int?>(null)
    val profileSelectedPosition: StateFlow<Int?>
        get() = _profileSelectedPosition

    fun selectProfile(position: Int) {
        _profileSelectedPosition.value = position
    }
}
