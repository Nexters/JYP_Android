package com.jyp.feature_add_place.data.mapper

import com.jyp.core.search_place.domain.model.SearchPlaceResponseModel
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel


fun SearchPlaceResponseModel.Place.toDataModelList() = SearchPlaceResultModel(
    name = this.place_name,
    address = this.address_name,
    category = this.category_group_name,
    x = this.x.toDouble(),
    y = this.y.toDouble(),
    infoUrl = this.place_url
)