package com.jyp.feature_add_place.util

import com.jyp.feature_add_place.R

enum class JourneyPikiPlaceCategoryEnum(
    val journeyPikiCategoryNameRes: Int,
    val categoryIconRes: Int,
) {
    MART(
        journeyPikiCategoryNameRes = R.string.category_name_mart,
        categoryIconRes = R.drawable.icon_mart
    ),
    CONVENIENCE_STORE(
        journeyPikiCategoryNameRes = R.string.category_name_convenience_store,
        categoryIconRes = R.drawable.icon_convenience_store
    ),
    SCHOOL(
        journeyPikiCategoryNameRes = R.string.category_name_school,
        categoryIconRes = R.drawable.icon_school
    ),
    TRANSPORTATION(
        journeyPikiCategoryNameRes = R.string.category_name_transportation,
        categoryIconRes = R.drawable.icon_transportation
    ),
    CULTURAL_INSTITUTION(
        journeyPikiCategoryNameRes = R.string.category_name_cultural_institution,
        categoryIconRes = R.drawable.icon_cultural_institution
    ),
    PUBLIC_INSTITUTION(
        journeyPikiCategoryNameRes = R.string.category_name_public_institution,
        categoryIconRes = R.drawable.icon_public_institution
    ),
    TOUR_SPOT(
        journeyPikiCategoryNameRes = R.string.category_name_tour_spot,
        categoryIconRes = R.drawable.icon_tour_spot
    ),
    LODGING(
        journeyPikiCategoryNameRes = R.string.category_name_lodging,
        categoryIconRes = R.drawable.icon_lodging
    ),
    RESTAURANT(
        journeyPikiCategoryNameRes = R.string.category_name_restaurant,
        categoryIconRes = R.drawable.icon_restaurant
    ),
    CAFE(
        journeyPikiCategoryNameRes = R.string.category_name_cafe,
        categoryIconRes = R.drawable.icon_cafe
    ),
    HOSPITAL(
        journeyPikiCategoryNameRes = R.string.category_name_hospital,
        categoryIconRes = R.drawable.icon_hospital
    ),
    PHARMACY(
        journeyPikiCategoryNameRes = R.string.category_name_pharmacy,
        categoryIconRes = R.drawable.icon_pharmacy
    ),
    BANK(
        journeyPikiCategoryNameRes = R.string.category_name_bank,
        categoryIconRes = R.drawable.icon_bank
    ),
    CHARGING_ZONE(
        journeyPikiCategoryNameRes = R.string.category_name_charging_zone,
        categoryIconRes = R.drawable.icon_charging_zone
    ),
    PARKING_LOT(
        journeyPikiCategoryNameRes = R.string.category_name_parking_lot,
        categoryIconRes = R.drawable.icon_parking_lot
    ),
    ETC(
        journeyPikiCategoryNameRes = R.string.category_name_etc,
        categoryIconRes = R.drawable.icon_etc
    )
}
