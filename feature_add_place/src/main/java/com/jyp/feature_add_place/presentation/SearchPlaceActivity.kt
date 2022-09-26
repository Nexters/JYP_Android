package com.jyp.feature_add_place.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jyp.feature_add_place.presentation.viewmodel.SearchPlaceViewModel
import com.jyp.feature_add_place.util.checkNetworkStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchPlaceActivity : ComponentActivity() {

    private val viewModel by viewModels<SearchPlaceViewModel>()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                context = this,
                searchPlaceViewModel = viewModel,
                onClickBackButton = { this.finish() }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
private fun Screen(
    context: Context,
    searchPlaceViewModel: SearchPlaceViewModel,
    onClickBackButton: () -> Unit
) {
    SearchPlaceScreen(
        uiState = searchPlaceViewModel.uiState.collectAsState().value,
        onPlaceNameChanged = { placeName ->
            context.checkNetworkStatus(
                isActivated = { searchPlaceViewModel.getSearchPlaceResult(placeName) },
                isInactivated = { }
            )
        },
        onClickBackButton = onClickBackButton
    )
}