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
import com.jyp.feature_planner.presentation.add_planner_route.AddPlannerRouteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlannerActivity : ComponentActivity() {
    private val viewModel: PlannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                    viewModel = viewModel,
                    onClickEditRoute = {
                        startActivity(Intent(this, AddPlannerRouteActivity::class.java))
                    }
            )
        }

        intent.getStringExtra(EXTRA_PLANNER_ID)?.let {
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
        onClickEditRoute: () -> Unit,
) {
    val pikMes by viewModel.pikMes.collectAsState()
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
                    newPikMeClick = {
                        viewModel.fetchPikMes()
                    },
                    onClickEditRoute = onClickEditRoute,
            )
        }
    }
}
