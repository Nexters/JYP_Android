package com.jyp.feature_planner.presentation.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
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
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(viewModel: PlannerViewModel) {
    val pikMes by viewModel.pikMes.collectAsState()
    val tags by viewModel.tags.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedTag by remember {
        mutableStateOf(tags.firstOrNull())
    }

    BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                selectedTag?.let {
                    TagSelectedBottomSheetScreen(tag = it)
                }
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Box(
                modifier = Modifier.fillMaxSize()
        ) {
            PlannerScreen(
                    pikMes = pikMes,
                    tags = tags,
                    tagClick = {
                        selectedTag = it
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
            )
            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(JypColors.Black20)
                )
            }
        }
    }
}
