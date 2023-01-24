package com.jyp.feature_add_place.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.JypBaseResponse
import com.jyp.core_network.jyp.model.enumerate.PlaceCategory
import com.jyp.core_network.jyp.model.request.AddPlaceRequestBody
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.util.JourneyPikiPlaceCategoryEnum
import javax.inject.Inject

class PlaceMapDataSource @Inject constructor(
    private val jypApi: JypApi,
) {
    suspend fun addPikme(
        plannerId: String,
        searchPlaceResultModel: SearchPlaceResultModel,
    ): JypBaseResponse<Any> {
        val addPlaceRequestBody = AddPlaceRequestBody(
            name = searchPlaceResultModel.name,
            address = searchPlaceResultModel.address,
            category = mapCategory(searchPlaceResultModel.categoryEnum),
            longitude = searchPlaceResultModel.longitude,
            latitude = searchPlaceResultModel.latitude,
            link = searchPlaceResultModel.infoUrl
        )

        return jypApi.addPikme(plannerId, addPlaceRequestBody)
    }

    private fun mapCategory(categoryEnum: JourneyPikiPlaceCategoryEnum): PlaceCategory {
        return when (categoryEnum) {
            JourneyPikiPlaceCategoryEnum.MART -> PlaceCategory.M
            JourneyPikiPlaceCategoryEnum.CONVENIENCE_STORE ->PlaceCategory.CS
            JourneyPikiPlaceCategoryEnum.SCHOOL -> PlaceCategory.S
            JourneyPikiPlaceCategoryEnum.TRANSPORTATION -> PlaceCategory.T
            JourneyPikiPlaceCategoryEnum.CULTURAL_INSTITUTION -> PlaceCategory.CI
            JourneyPikiPlaceCategoryEnum.PUBLIC_INSTITUTION -> PlaceCategory.PI
            JourneyPikiPlaceCategoryEnum.TOUR_SPOT -> PlaceCategory.TS
            JourneyPikiPlaceCategoryEnum.LODGING -> PlaceCategory.L
            JourneyPikiPlaceCategoryEnum.RESTAURANT -> PlaceCategory.R
            JourneyPikiPlaceCategoryEnum.CAFE -> PlaceCategory.C
            JourneyPikiPlaceCategoryEnum.HOSPITAL -> PlaceCategory.H
            JourneyPikiPlaceCategoryEnum.PHARMACY -> PlaceCategory.P
            JourneyPikiPlaceCategoryEnum.BANK -> PlaceCategory.B
            JourneyPikiPlaceCategoryEnum.CHARGING_ZONE -> PlaceCategory.CZ
            JourneyPikiPlaceCategoryEnum.PARKING_LOT -> PlaceCategory.P
            JourneyPikiPlaceCategoryEnum.ETC -> PlaceCategory.ETC
        }
    }
}
