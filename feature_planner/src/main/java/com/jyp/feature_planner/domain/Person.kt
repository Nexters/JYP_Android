package com.jyp.feature_planner.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
        val name: String,
        val profileUrl: String,
): Parcelable
