package com.jyp.feature_add_place.presentation

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.core_network.jyp.UiState
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.EXTRA_PLANNER_ID
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.SEARCH_PLACE_RESULT
import com.jyp.feature_add_place.presentation.viewmodel.PlaceMapViewModel
import com.jyp.feature_add_place.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlaceMapActivity : ComponentActivity() {

    private val viewModel: PlaceMapViewModel by viewModels()

    private val searchPlaceResultModel: SearchPlaceResultModel? by lazy {
        intent.getParcelableExtra(SEARCH_PLACE_RESULT)
    }

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStateFlowCollector()
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
                        searchPlaceResultModel?.let {
                            setIntentTo(PlaceInfoActivity::class.java) {
                                putExtra(SEARCH_PLACE_RESULT, searchPlaceResultModel)
                            }
                        }
                    },
                    onClickAddPlaceButton = {
                        (plannerId to searchPlaceResultModel).let { (id, model) ->
                            id ?: return@let
                            model ?: return@let
                            viewModel.addPikme(id, model)
                        }
                    }
                )
            }
        }
    }

    private fun initStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addPikmeUiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            startActivity(
                                Intent().apply {
                                    setClassName(
                                        this@PlaceMapActivity,
                                        "com.jyp.feature_planner.presentation.planner.PlannerActivity"
                                    )
                                    putExtra(EXTRA_PLANNER_ID, plannerId)
                                    flags = FLAG_ACTIVITY_CLEAR_TOP
                                }
                            )
                            finish()
                        }
                        is UiState.Failure -> uiState.throwable.printStackTrace()
                    }
                }
            }
        }
    }
}

@Composable
private fun Screen(
    searchPlaceResultModel: SearchPlaceResultModel,
    onClickBackButton: () -> Unit,
    onClickClearButton: () -> Unit,
    onClickInfoButton: () -> Unit,
    onClickAddPlaceButton: () -> Unit,
) {
    PlaceMapScreen(
        searchPlaceResultModel,
        onClickBackButton,
        onClickClearButton,
        onClickInfoButton,
        onClickAddPlaceButton,
    )
}
