package com.jyp.jyp_design.ui.tag

import androidx.compose.ui.graphics.Color
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import java.io.Serializable

sealed interface TagType : Serializable {
    val iconRes: Int

    fun getBackgroundColor(state: TagState): Color
    fun getTextColor(state: TagState): Color
    fun getIconColor(state: TagState): Color
    fun getIconBackgroundColor(state: TagState): Color

    object Soso : TagType {
        override val iconRes: Int = R.drawable.icon_smile

        override fun getBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_orange100
                TagState.SELECTED -> JypColors.Tag_orange200
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }
        }

        override fun getTextColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_orange300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_orange300
                TagState.SELECTED -> JypColors.Tag_brown200
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Tag_yellow100
                TagState.DISABLED -> JypColors.Text_white
            }
        }
    }

    object Like : TagType {
        override val iconRes: Int = R.drawable.icon_heart

        override fun getBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_blue100
                TagState.SELECTED -> JypColors.Tag_blue100
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }
        }

        override fun getTextColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Sub_blue300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Sub_blue300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Sub_blue300
                TagState.DISABLED -> JypColors.Text_white
            }
        }
    }

    object Dislike : TagType {
        override val iconRes: Int = R.drawable.icon_smile

        override fun getBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_white_red100
                TagState.SELECTED -> JypColors.Tag_red200
                TagState.DISABLED -> JypColors.Tag_white_grey100
            }
        }

        override fun getTextColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_red300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Tag_red300
                TagState.SELECTED -> JypColors.Text_white
                TagState.DISABLED -> JypColors.Tag_grey200
            }
        }

        override fun getIconBackgroundColor(state: TagState): Color {
            return when (state) {
                TagState.DEFAULT -> JypColors.Text_white
                TagState.SELECTED -> JypColors.Tag_red300
                TagState.DISABLED -> JypColors.Text_white
            }
        }
    }
}
