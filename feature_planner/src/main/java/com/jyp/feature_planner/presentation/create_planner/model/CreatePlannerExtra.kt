package com.jyp.feature_planner.presentation.create_planner.model

import android.os.Parcelable
import com.jyp.jyp_design.enumerate.ThemeType
import kotlinx.parcelize.Parcelize

sealed interface CreatePlannerExtra {
    @Parcelize
    data class Create(
        val title: String? = null,
        val themeType: ThemeType? = null,
        val startDateMillis: Long? = null,
        val endDateMillis: Long? = null,
    ) : Parcelable

    @Parcelize
    data class Join(
        val plannerId: String,
    ) : Parcelable

    @Parcelize
    data class Edit(
        val plannerId: String,
    ) : Parcelable
}