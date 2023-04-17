package com.jyp.main.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jyp.main.R

enum class BottomNavItem(
    @DrawableRes val activatedIconRes: Int,
    @DrawableRes val inactivatedIconRes: Int,
    @StringRes val labelRes: Int,
) {
    MY_JOURNEY(
            R.drawable.icon_bottom_nav_my_journey_activated,
            R.drawable.icon_bottom_nav_my_journey_inactivated,
            R.string.bottom_nav_my_journey_label,
    ),

    ANOTHER_JOURNEY(
            R.drawable.icon_bottom_nav_another_journey_activated,
            R.drawable.icon_bottom_nav_another_journey_inactivated,
            R.string.bottom_nav_another_journey_label,
    ),

    MY_PAGE(
            R.drawable.icon_bottom_nav_my_page_activated,
            R.drawable.icon_bottom_nav_my_page_inactivated,
            R.string.bottom_nav_my_page_label,
    );

    companion object {
        fun fromRoute(route: String?): BottomNavItem {
            return when (route) {
                MY_JOURNEY.name -> MY_JOURNEY
                ANOTHER_JOURNEY.name -> ANOTHER_JOURNEY
                MY_PAGE.name -> MY_PAGE

                else -> throw IllegalArgumentException("not allow route")
            }
        }
    }
}
