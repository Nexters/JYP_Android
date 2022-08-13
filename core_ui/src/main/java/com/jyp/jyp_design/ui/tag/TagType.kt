package com.jyp.jyp_design.ui.tag

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors

enum class TagType(
        @DrawableRes iconRes: Int,
        defaultBackgroundColor: Color,
        defaultTextColor: Color,
        defaultIconColor: Color,
        defaultIconBackgroundColor: Color,
        selectedBackgroundColor: Color,
        selectedTextColor: Color,
        selectedIconColor: Color,
        selectedIconBackgroundColor: Color,
        inactiveBackgroundColor: Color,
        inactiveTextColor: Color,
        inactiveIconColor: Color,
        inactiveIconBackgroundColor: Color,
) {
    SOSO(
            iconRes = R.drawable.icon_smile,
            defaultBackgroundColor = JypColors.Tag_white_blue100,
            defaultTextColor = JypColors.Sub_blue300,
            defaultIconColor = JypColors.Sub_blue300,
            defaultIconBackgroundColor = JypColors.Text_white,
            selectedBackgroundColor = JypColors.Tag_blue100,
            selectedTextColor = JypColors.Text_white,
            selectedIconColor = JypColors.Text_white,
            selectedIconBackgroundColor = JypColors.Sub_blue300,
            inactiveBackgroundColor = JypColors.Tag_white_grey100,
            inactiveTextColor = JypColors.Tag_grey200,
            inactiveIconColor = JypColors.Tag_grey200,
            inactiveIconBackgroundColor = JypColors.Text_white,
    ),
    LIKE(
            iconRes = R.drawable.icon_heart,
            defaultBackgroundColor = JypColors.Tag_white_red100,
            defaultTextColor = JypColors.Tag_red300,
            defaultIconColor = JypColors.Tag_red300,
            defaultIconBackgroundColor = JypColors.Text_white,
            selectedBackgroundColor = JypColors.Tag_red200,
            selectedTextColor = JypColors.Text_white,
            selectedIconColor = JypColors.Text_white,
            selectedIconBackgroundColor = JypColors.Tag_red300,
            inactiveBackgroundColor = JypColors.Tag_white_grey100,
            inactiveTextColor = JypColors.Tag_grey200,
            inactiveIconColor = JypColors.Tag_grey200,
            inactiveIconBackgroundColor = JypColors.Text_white,
    ),
    DISLIKE(
            iconRes = R.drawable.icon_close,
            defaultBackgroundColor = JypColors.Tag_white_blue100,
            defaultTextColor = JypColors.Tag_orange300,
            defaultIconColor = JypColors.Tag_orange300,
            defaultIconBackgroundColor = JypColors.Text_white,
            selectedBackgroundColor = JypColors.Tag_blue100,
            selectedTextColor = JypColors.Text_white,
            selectedIconColor = JypColors.Tag_brown200,
            selectedIconBackgroundColor = JypColors.Tag_yellow100,
            inactiveBackgroundColor = JypColors.Tag_white_grey100,
            inactiveTextColor = JypColors.Tag_grey200,
            inactiveIconColor = JypColors.Tag_grey200,
            inactiveIconBackgroundColor = JypColors.Text_white,
    ),
}
