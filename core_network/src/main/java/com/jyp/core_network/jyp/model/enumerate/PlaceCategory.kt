package com.jyp.core_network.jyp.model.enumerate

import androidx.annotation.DrawableRes
import com.jyp.core.R

enum class PlaceCategory {
    M,
    CS,
    S,
    T,
    CI,
    PI,
    TS,
    L,
    R,
    C,
    H,
    P,
    B,
    CZ,
    PL,
    ETC,
}

fun PlaceCategory.toName(): String {
    return when (this) {
        PlaceCategory.M -> "마트"
        PlaceCategory.CS -> "편의점"
        PlaceCategory.S -> "학교"
        PlaceCategory.T -> "교통"
        PlaceCategory.CI -> "문화시설"
        PlaceCategory.PI -> "공공기관"
        PlaceCategory.TS -> "관광지"
        PlaceCategory.L -> "숙소"
        PlaceCategory.R -> "음식점"
        PlaceCategory.C -> "카페"
        PlaceCategory.H -> "병원"
        PlaceCategory.P -> "약국"
        PlaceCategory.B -> "은행"
        PlaceCategory.CZ -> "충전소"
        PlaceCategory.PL -> "주차장"
        PlaceCategory.ETC -> "기타"
    }
}

fun String.toPlaceCategory(): PlaceCategory {
    return when (this) {
        "마트" -> PlaceCategory.M
        "편의점" -> PlaceCategory.CS
        "학교" -> PlaceCategory.S
        "교통" -> PlaceCategory.T
        "문화시설" -> PlaceCategory.CI
        "공공기관" -> PlaceCategory.PI
        "관광지" -> PlaceCategory.TS
        "숙소" -> PlaceCategory.L
        "음식점" -> PlaceCategory.R
        "카페" -> PlaceCategory.C
        "병원" -> PlaceCategory.H
        "약국" -> PlaceCategory.P
        "은행" -> PlaceCategory.B
        "충전소" -> PlaceCategory.CZ
        "주차장" -> PlaceCategory.PL
        else -> PlaceCategory.ETC
    }
}

@DrawableRes
fun PlaceCategory.getDrawableResourceId(): Int {
    return when (this) {
        PlaceCategory.M -> R.drawable.icon_mart
        PlaceCategory.CS -> R.drawable.icon_convenience_store
        PlaceCategory.S -> R.drawable.icon_school
        PlaceCategory.T -> R.drawable.icon_transportation
        PlaceCategory.CI -> R.drawable.icon_cultural_institution
        PlaceCategory.PI -> R.drawable.icon_public_institution
        PlaceCategory.TS -> R.drawable.icon_tour_spot
        PlaceCategory.L -> R.drawable.icon_lodging
        PlaceCategory.R -> R.drawable.icon_restaurant
        PlaceCategory.C -> R.drawable.icon_cafe
        PlaceCategory.H -> R.drawable.icon_hospital
        PlaceCategory.P -> R.drawable.icon_pharmacy
        PlaceCategory.B -> R.drawable.icon_bank
        PlaceCategory.CZ -> R.drawable.icon_charging_zone
        PlaceCategory.PL -> R.drawable.icon_parking_lot
        PlaceCategory.ETC -> R.drawable.icon_etc
    }
}
