package com.jyp.feature_planner.presentation.planner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jyp.feature_add_place.presentation.SearchPlaceActivity
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlannerActivity : ComponentActivity() {
    private val viewModel: PlannerViewModel by viewModels()

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickInviteUserButton = {
                    startActivity(Intent(this, InviteUserActivity::class.java))
                },
                viewModel = viewModel,
                onNewPikMeClick = {
                    startActivity(
                        Intent(this, SearchPlaceActivity::class.java).apply {
                            putExtra(SearchPlaceActivity.EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
                onClickEditRoute = { index ->
                    startActivity(
                        Intent(this, AddPlannerRouteActivity::class.java).apply {
                            putExtra(
                                AddPlannerRouteActivity.EXTRA_PIKMIS,
                                ArrayList(viewModel.pikmis.value)
                            )

                            putExtra(
                                AddPlannerRouteActivity.EXTRA_PIKIS,
                                ArrayList(viewModel.planItems.value[index].pikis)
                            )

                            putExtra(AddPlannerRouteActivity.EXTRA_JOURNEY_ID, plannerId)
                            putExtra(AddPlannerRouteActivity.EXTRA_DAY_INDEX, index)
                        }
                    )
                },
                onClickBackButton = this::finish
            )
        }
    }

    override fun onResume() {
        super.onResume()

        plannerId?.let {
            viewModel.fetchPlannerData(it)
        }
    }

    companion object {
        const val EXTRA_PLANNER_ID = "EXTRA_PLANNER_ID"
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    viewModel: PlannerViewModel,
    onClickInviteUserButton: () -> Unit,
    onClickEditRoute: (day: Int) -> Unit,
    onNewPikMeClick: () -> Unit,
    onClickBackButton: () -> Unit,
) {
    val plannerDates by viewModel.plannerDates.collectAsState()
    val pikMes by viewModel.pikmis.collectAsState()
    val tags by viewModel.tags.collectAsState()
    val membersProfileUrl by viewModel.membersProfileUrl.collectAsState()
    val planItems by viewModel.planItems.collectAsState()

    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedTag by remember {
        mutableStateOf(tags.firstOrNull())
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        sheetContent = {
            selectedTag?.let {
                TagSelectedBottomSheetScreen(tag = it)
            } ?: Spacer(modifier = Modifier.size(1.dp))
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            PlannerScreen(
                startDate = plannerDates.first,
                endDate = plannerDates.second,
                pikMes = pikMes,
                joinMembers = membersProfileUrl,
                tags = tags,
                tagClick = {
                    selectedTag = it
                    coroutineScope.launch {
                        if (!bottomSheetScaffoldState.isVisible) {
                            bottomSheetScaffoldState.show()
                        }
                    }
                },
                planItems = planItems,
                newPikMeClick = onNewPikMeClick,
                onClickEditRoute = onClickEditRoute,
                onClickInviteUserButton = onClickInviteUserButton,
                onClickBackButton = onClickBackButton,
            )
        }
    }
}
