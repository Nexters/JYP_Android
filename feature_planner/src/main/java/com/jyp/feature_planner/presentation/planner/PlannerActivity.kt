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
import com.jyp.core_util.extensions.setIntentTo
import com.jyp.feature_add_place.presentation.PlaceInfoActivity
import com.jyp.feature_add_place.presentation.SearchPlaceActivity
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.PLACE_INFO_NAME
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.PLACE_INFO_URL
import com.jyp.feature_planner.domain.PlannerPikme
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity.Companion.EXTRA_DAY_INDEX
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity.Companion.EXTRA_JOURNEY_ID
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity.Companion.EXTRA_PIKIS
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity.Companion.EXTRA_PIKMIS
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity.Companion.EXTRA_START_DATE
import com.jyp.feature_planner.presentation.create_planner.CreatePlannerActivity
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerAction
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerStep
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlannerActivity : ComponentActivity() {
    private val viewModel: PlannerViewModel by viewModels()

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }
    private val isDDay: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_IS_D_DAY, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                isDDay = isDDay,
                onClickInviteUserButton = {
                    startActivity(
                        Intent(this, InviteUserActivity::class.java).apply {
                            putExtra(EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
                viewModel = viewModel,
                onNewPikMeClick = {
                    startActivity(
                        Intent(this, SearchPlaceActivity::class.java).apply {
                            putExtra(EXTRA_PLANNER_ID, plannerId)
                        }
                    )
                },
                onClickEditRoute = { index ->
                    startActivity(
                        Intent(this, AddPlannerRouteActivity::class.java).apply {
                            putExtra(EXTRA_PIKMIS, ArrayList(viewModel.pikmis.value))
                            putExtra(EXTRA_PIKIS, ArrayList(viewModel.planItems.value[index].pikis))

                            putExtra(EXTRA_JOURNEY_ID, plannerId)
                            putExtra(EXTRA_DAY_INDEX, index)
                            putExtra(EXTRA_START_DATE, viewModel.plannerDates.value.first)
                            putExtra(
                                AddPlannerRouteActivity.EXTRA_PIKIS,
                                ArrayList(viewModel.planItems.value[index].pikis)
                            )

                            putExtra(AddPlannerRouteActivity.EXTRA_JOURNEY_ID, plannerId)
                            putExtra(AddPlannerRouteActivity.EXTRA_DAY_INDEX, index)
                            putExtra(
                                AddPlannerRouteActivity.EXTRA_START_DATE,
                                viewModel.plannerDates.value.first
                            )
                        }
                    )
                },
                onClickBackButton = this::finish,
                onClickInfo = { plannerPikme ->
                    setIntentTo(PlaceInfoActivity::class.java) {
                        putString(PLACE_INFO_NAME, plannerPikme.title)
                        putString(PLACE_INFO_URL, plannerPikme.link)
                    }
                },
                onClickLike = { plannerPikme ->
                    plannerId?.let {
                        viewModel.switchPikmeLike(it, plannerPikme)
                    }
                },
                onClickEditTag = {
                    plannerId?.let {
                        startActivity(
                            Intent(this, CreatePlannerActivity::class.java).apply {
                                putExtra(CreatePlannerActivity.EXTRA_CREATE_PLANNER_STEP, CreatePlannerStep.TASTE)
                                putExtra(CreatePlannerActivity.EXTRA_CREATE_PLANNER_ACTION, CreatePlannerAction.Edit(it))
                            }
                        )
                    }
                },
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
        const val EXTRA_IS_D_DAY = "EXTRA_IS_D_DAY"
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    isDDay: Boolean,
    viewModel: PlannerViewModel,
    onClickInviteUserButton: () -> Unit,
    onClickEditRoute: (day: Int) -> Unit,
    onNewPikMeClick: () -> Unit,
    onClickBackButton: () -> Unit,
    onClickInfo: (PlannerPikme) -> Unit,
    onClickLike: (PlannerPikme) -> Unit,
    onClickEditTag: () -> Unit,
) {
    val plannerTitle by viewModel.plannerTitle.collectAsState()
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
                isDDay = isDDay,
                plannerTitle = plannerTitle,
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
                onClickInfo = onClickInfo,
                onClickLike = onClickLike,
                onClickEditTag = onClickEditTag,
            )
        }
    }
}
