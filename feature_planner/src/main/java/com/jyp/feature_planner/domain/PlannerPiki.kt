package com.jyp.feature_planner.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlannerPiki(
        val placeName: String,
        val address: String,
): Parcelable
