package com.jyp.feature_add_place.data.model

import android.os.Parcelable
import com.jyp.feature_add_place.util.JourneyPikiPlaceCategoryEnum
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchPlaceResultModel(
    val name: String,
    val address: String,
    val categoryEnum: JourneyPikiPlaceCategoryEnum,
    val latitude: Double,
    val longitude: Double,
    val infoUrl: String
): Parcelable