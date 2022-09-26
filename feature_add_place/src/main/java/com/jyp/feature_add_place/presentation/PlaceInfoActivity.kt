package com.jyp.feature_add_place.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.feature_add_place.PlaceInfoScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaceInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickCloseButton = { this.finish() }
            )
        }
    }
}

@Composable
private fun Screen(onClickCloseButton: () -> Unit) {
    PlaceInfoScreen(
        onClickCloseButton = onClickCloseButton
    )
}
