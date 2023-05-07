package com.jyp.feature_planner.presentation.create_planner.model

import com.jyp.jyp_design.ui.tag.TagType

sealed class CreatePlannerBottomSheetType {
    object None : CreatePlannerBottomSheetType()
    object SelectTheme : CreatePlannerBottomSheetType()
    data class AddTag(val tagType: TagType) : CreatePlannerBottomSheetType()
}
