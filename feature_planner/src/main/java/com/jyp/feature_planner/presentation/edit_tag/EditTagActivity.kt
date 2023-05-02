package com.jyp.feature_planner.presentation.edit_tag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jyp.core_network.jyp.UiState
import com.jyp.feature_planner.domain.Person
import com.jyp.feature_planner.presentation.create_planner.AddTagBottomSheetScreen
import com.jyp.feature_planner.presentation.create_planner.model.CreatePlannerBottomSheetType
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_PLANNER_ID
import com.jyp.feature_planner.presentation.planner.PlannerActivity.Companion.EXTRA_USER_INFO
import com.jyp.feature_planner.presentation.planner.TagSelectedBottomSheetScreen
import com.jyp.jyp_design.resource.JypColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EditTagActivity : ComponentActivity() {

    private val viewModel: EditTagViewModel by viewModels()

    private val plannerId: String? by lazy {
        intent.getStringExtra(EXTRA_PLANNER_ID)
    }
    private val user: Person? by lazy {
        intent.getParcelableExtra(EXTRA_USER_INFO)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                viewModel = viewModel,
                onClickBackButton = { this.finish() }
            )
        }

        user?.let { user ->
            plannerId?.let { plannerId ->
                viewModel.getTags(user, plannerId)
            }
        }
        initEditTagUiState()
    }

    private fun initEditTagUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editTagsUiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {}
                        is UiState.Success -> finish()
                        is UiState.Failure -> {
                            uiState.throwable.printStackTrace()
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen(
    viewModel: EditTagViewModel,
    onClickBackButton: () -> Unit
) {
    val entireTags by viewModel.entireTags.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var bottomSheetType by remember {
        mutableStateOf<CreatePlannerBottomSheetType>(CreatePlannerBottomSheetType.None)
    }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { false }
    )

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetType = CreatePlannerBottomSheetType.None
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            when (val type = bottomSheetType) {
                is CreatePlannerBottomSheetType.AddTag -> AddTagBottomSheetScreen(
                    modifier = Modifier.fillMaxHeight(0.9f),
                    tagType = type.tagType,
                    onClickCancel = {
                        coroutineScope.launch {
                            bottomSheetType = CreatePlannerBottomSheetType.None
                            bottomSheetState.hide()
                        }
                    },
                    onClickSubmit = { tag ->
                        viewModel.addTagToEntireTags(tag)
                        coroutineScope.launch {
                            bottomSheetType = CreatePlannerBottomSheetType.None
                            bottomSheetState.hide()
                        }
                    }
                )
                else -> Box(
                    modifier = Modifier
                        .background(JypColors.Background_grey300)
                        .size(1.dp)
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            EditTagScreen(
                entireTags = entireTags,
                onClickBackAction = onClickBackButton,
                onClickTagItem = { viewModel.clickTag(it) },
                onClickCreateTag = {
                    coroutineScope.launch {
                        bottomSheetType = CreatePlannerBottomSheetType.AddTag(it)
                        bottomSheetState.show()
                    }
                },
                onclickEditButton = { viewModel.editTags() }
            )
        }
    }
}