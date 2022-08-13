package com.jyp.jyp_design.ui.tag

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors

sealed interface TagType {
    val iconRes: Int
    var state: TagState

    val backgroundColor: Color
    val textColor: Color
    val iconColor: Color
    val iconBackgroundColor: Color

    class Soso : TagType {
        override val iconRes: Int = R.drawable.icon_smile
        override var state: TagState = TagState.DEFAULT

        override val backgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_orange100
                TagState.SELECTED -> JypColors.Tag_orange200
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }

        override val textColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_orange300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_orange300
                TagState.SELECTED -> JypColors.Tag_brown200
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconBackgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Tag_yellow100
                TagState.DISABLED -> JypColors.Text_white
            }
    }

    class Like : TagType {
        override val iconRes: Int = R.drawable.icon_heart
        override var state: TagState = TagState.DEFAULT

        override val backgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_blue100
                TagState.SELECTED -> JypColors.Tag_blue100
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }

        override val textColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Sub_blue300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Sub_blue300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconBackgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Sub_blue300
                TagState.DISABLED -> JypColors.Text_white
            }
    }

    class Dislike : TagType {
        override val iconRes: Int = R.drawable.icon_smile
        override var state: TagState = TagState.DEFAULT

        override val backgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_red100
                TagState.SELECTED -> JypColors.Tag_red200
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }

        override val textColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_red300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Tag_red300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }

        override val iconBackgroundColor: Color
            get() = when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Tag_red300
                TagState.DISABLED -> JypColors.Text_white
            }
    }
}
