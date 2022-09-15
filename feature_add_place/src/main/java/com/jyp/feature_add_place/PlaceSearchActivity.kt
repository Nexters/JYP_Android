package com.jyp.feature_add_place

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable


class PlaceSearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickBackButton = { this.finish() }
            )
        }
    }
}

@Composable
private fun Screen(
    onClickBackButton: () -> Unit
) {
    PlaceSearchScreen(
        onClickBackButton = onClickBackButton
    )
}