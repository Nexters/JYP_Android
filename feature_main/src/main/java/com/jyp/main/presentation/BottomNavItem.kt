package com.jyp.main.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jyp.main.R

enum class BottomNavItem(
        @DrawableRes val activeIconRes: Int,
        @DrawableRes val inactiveIconRes: Int,
        @StringRes val labelRes: Int,
) {
    MY_JOURNEY(
            R.drawable.ic_bottom_nav_my_journey_active,
            R.drawable.ic_bottom_nav_my_journey_inactive,
            R.string.bottom_nav_my_journey_label,
    ),

    ANOTHER_JOURNEY(
            R.drawable.ic_bottom_nav_another_journey_active,
            R.drawable.ic_bottom_nav_another_journey_inactive,
            R.string.bottom_nav_another_journey_label,
    ),

    MY_PAGE(
            R.drawable.ic_bottom_nav_my_page_active,
            R.drawable.ic_bottom_nav_my_page_inactive,
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
