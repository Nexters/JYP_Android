package com.jyp.feature_add_place.data.mapper

import com.jyp.core_network.kakao.response.KakaoLocalResponseModel
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.util.getJourneyPikiPlaceCategoryEnum


fun KakaoLocalResponseModel.Place.toSearchPlaceResultModel() = SearchPlaceResultModel(
    name = this.place_name,
    address = this.address_name,
    categoryEnum = this.category_group_name.getJourneyPikiPlaceCategoryEnum(),
    latitude = this.y.toDouble(),
    longitude = this.x.toDouble(),
    infoUrl = this.place_url
)