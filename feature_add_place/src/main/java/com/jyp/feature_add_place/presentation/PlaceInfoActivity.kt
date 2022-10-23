package com.jyp.feature_add_place.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.util.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaceInfoActivity : ComponentActivity() {

    private val searchPlaceResultModel: SearchPlaceResultModel? by lazy {
        intent.getParcelableExtra(SearchPlaceActivity.SEARCH_PLACE_RESULT)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (searchPlaceResultModel == null) {
            true -> {
                showToast(R.string.search_place_error)
                this.finish()
            }
            false -> setContent {
                Screen(
                    searchPlaceResult = searchPlaceResultModel!!,
                    onClickCloseButton = { this.finish() }
                )
            }
        }
    }
}

@Composable
private fun Screen(
    searchPlaceResult: SearchPlaceResultModel,
    onClickCloseButton: () -> Unit
) {
    PlaceInfoScreen(
        searchPlaceResultModel = searchPlaceResult,
        onClickCloseButton = onClickCloseButton
    )
}
