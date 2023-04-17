package com.jyp.feature_add_place.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.presentation.viewmodel.SearchPlaceViewModel
import com.jyp.feature_add_place.util.checkNetworkStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchPlaceActivity : ComponentActivity() {

    private val viewModel by viewModels<SearchPlaceViewModel>()

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                context = this,
                searchPlaceViewModel = viewModel,
                onClickPlaceItem = { searchPlaceResultModel ->
                    startActivity(
                        Intent(this, PlaceMapActivity::class.java).apply {
                            putExtra(SEARCH_PLACE_RESULT, searchPlaceResultModel)
                            putExtra(EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
                onClickBackButton = { this.finish() }
            )
        }
    }

    companion object {
        const val SEARCH_PLACE_RESULT = "SEARCH_PLACE_RESULT"
        const val PLACE_INFO_NAME = "PLACE_INFO_NAME"
        const val PLACE_INFO_URL = "PLACE_INFO_URL"
        const val EXTRA_PLANNER_ID = "EXTRA_PLANNER_ID"
    }
}

@Composable
private fun Screen(
    context: Context,
    searchPlaceViewModel: SearchPlaceViewModel,
    onClickPlaceItem: (SearchPlaceResultModel) -> Unit,
    onClickBackButton: () -> Unit
) {
    SearchPlaceScreen(
        apiState = searchPlaceViewModel.apiState.collectAsState().value,
        onPlaceNameChanged = { placeName ->
            context.checkNetworkStatus(
                isActivated = { searchPlaceViewModel.getSearchPlaceResult(placeName) },
                isInactivated = { }
            )
        },
        onClickPlaceItem = onClickPlaceItem,
        onClickBackButton = onClickBackButton
    )
}
