package com.jyp.main.presentation

import androidx.compose.runtime.Composable

data class MainScreenItem(
        val navItem: BottomNavItem,
        val content: @Composable () -> Unit,
)
