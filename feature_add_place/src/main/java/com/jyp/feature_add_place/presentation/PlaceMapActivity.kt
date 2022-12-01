package com.jyp.feature_add_place.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.SEARCH_PLACE_RESULT
import com.jyp.feature_add_place.util.showToast


class PlaceMapActivity : ComponentActivity() {

    private val searchPlaceResultModel: SearchPlaceResultModel? by lazy {
        intent.getParcelableExtra(SEARCH_PLACE_RESULT)
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
                    searchPlaceResultModel = searchPlaceResultModel!!,
                    onClickBackButton = { this.finish() },
                    onClickClearButton = {
                        startActivity(
                            Intent(this@PlaceMapActivity, SearchPlaceActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                        )
                    },
                    onClickInfoButton = {
                        startActivity(
                            Intent(this, PlaceInfoActivity::class.java).apply {
                                putExtra(SEARCH_PLACE_RESULT, searchPlaceResultModel)
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun Screen(
    searchPlaceResultModel: SearchPlaceResultModel,
    onClickBackButton: () -> Unit,
    onClickClearButton: () -> Unit,
    onClickInfoButton: () -> Unit

) {
    PlaceMapScreen(
        searchPlaceResultModel,
        onClickBackButton,
        onClickClearButton,
        onClickInfoButton
    )
}