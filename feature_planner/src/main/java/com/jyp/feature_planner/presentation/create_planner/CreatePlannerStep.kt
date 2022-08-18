package com.jyp.feature_planner.presentation.create_planner

import androidx.annotation.StringRes
import com.jyp.feature_planner.R

enum class CreatePlannerStep(
        @StringRes val navigationTitleRes: Int,
        @StringRes val titleRes: Int,
        @StringRes val descriptionRes: Int,
) {
    TITLE(
            R.string.create_planner_name_navigation_title,
            R.string.create_planner_name_title,
            R.string.create_planner_name_description,
    ),
    DATE(
            R.string.create_planner_date_navigation_title,
            R.string.create_planner_date_title,
            R.string.create_planner_date_description,
    ),
    TASTE(
            R.string.create_planner_taste_navigation_title,
            R.string.create_planner_taste_title,
            R.string.create_planner_taste_description,
    ),
}
