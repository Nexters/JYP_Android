package com.jyp.feature_planner.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlannerPikme(
        val title: String,
        val address: String,
        val category: String,
        val likeCount: Int,
): Parcelable
