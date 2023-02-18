package com.jyp.feature_planner.domain

import android.os.Parcelable
import com.jyp.core_network.jyp.model.enumerate.PlaceCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlannerPiki(
        val id: String?,
        val name: String,
        val address: String,
        val category: String,
        val categoryIconRes: Int = 0,
        val longitude: Double,
        val latitude: Double,
        val link: String,
): Parcelable
