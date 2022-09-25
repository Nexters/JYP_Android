package com.jyp.feature_add_place.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.jyp.feature_add_place.presentation.viewmodel.SearchPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchPlaceActivity : ComponentActivity() {

    private val viewModel by viewModels<SearchPlaceViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                searchPlaceViewModel = viewModel,
                onClickBackButton = { this.finish() }
            )
        }
    }
}

@Composable
private fun Screen(
    searchPlaceViewModel: SearchPlaceViewModel,
    onClickBackButton: () -> Unit
) {
    val viewModel = remember { searchPlaceViewModel }

    SearchPlaceScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onPlaceNameChanged = { placeName ->
            searchPlaceViewModel.getSearchPlaceResult(placeName)
        },
        onClickBackButton = onClickBackButton
    )
}