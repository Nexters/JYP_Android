package com.jyp.feature_add_place.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.util.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaceInfoActivity : ComponentActivity() {

    private val placeInfoName: String? by lazy {
        intent.getStringExtra(SearchPlaceActivity.PLACE_INFO_NAME)
    }
    private val placeInfoUrl: String? by lazy {
        intent.getStringExtra(SearchPlaceActivity.PLACE_INFO_URL)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (placeInfoName == null || placeInfoUrl == null) {
            true -> {
                showToast(R.string.search_place_error)
                this.finish()
            }
            false -> setContent {
                Screen(
                    placeInfoName = placeInfoName!!,
                    placeInfoUrl = placeInfoUrl!!,
                    onClickCloseButton = { this.finish() }
                )
            }
        }
    }
}

@Composable
private fun Screen(
    placeInfoName: String,
    placeInfoUrl: String,
    onClickCloseButton: () -> Unit
) {
    PlaceInfoScreen(
        placeInfoName = placeInfoName,
        placeInfoUrl = placeInfoUrl,
        onClickCloseButton = onClickCloseButton
    )
}
