package com.jyp.feature_add_place.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.jyp.feature_add_place.R


fun String.getJourneyPikiPlaceCategoryEnum(): JourneyPikiPlaceCategoryEnum {
    return when (this) {
        "대형마트" -> JourneyPikiPlaceCategoryEnum.MART
        "편의점" -> JourneyPikiPlaceCategoryEnum.CONVENIENCE_STORE
        "학교" -> JourneyPikiPlaceCategoryEnum.SCHOOL
        "지하철역" -> JourneyPikiPlaceCategoryEnum.TRANSPORTATION
        "문화시설" -> JourneyPikiPlaceCategoryEnum.CULTURAL_INSTITUTION
        "공공기관" -> JourneyPikiPlaceCategoryEnum.PUBLIC_INSTITUTION
        "관광명소" -> JourneyPikiPlaceCategoryEnum.TOUR_SPOT
        "숙박" -> JourneyPikiPlaceCategoryEnum.LODGING
        "음식점" -> JourneyPikiPlaceCategoryEnum.RESTAURANT
        "카페" -> JourneyPikiPlaceCategoryEnum.CAFE
        "병원" -> JourneyPikiPlaceCategoryEnum.HOSPITAL
        "약국" -> JourneyPikiPlaceCategoryEnum.PHARMACY
        "은행" -> JourneyPikiPlaceCategoryEnum.BANK
        "충전소" -> JourneyPikiPlaceCategoryEnum.CHARGING_ZONE
        "주차장" -> JourneyPikiPlaceCategoryEnum.PARKING_LOT
        else -> JourneyPikiPlaceCategoryEnum.ETC
    }
}

@RequiresApi(Build.VERSION_CODES.M)
inline fun Context.checkNetworkStatus(
    isActivated: () -> Unit,
    isInactivated: (() -> Unit) = { }
) {
    when (isNetworkActivated(this)) {
        true -> isActivated()
        false -> {
            isInactivated()
            showToast(R.string.common_toast_check_network_status)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun isNetworkActivated(context: Context): Boolean {
    val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        true -> connectivityManager.activeNetwork != null
        false -> {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}

private var toast: Toast? = null
fun Context.showToast(messageRes: Int) {
    toast?.cancel()
    toast = Toast.makeText(applicationContext, messageRes, Toast.LENGTH_SHORT)
    toast?.show()
}