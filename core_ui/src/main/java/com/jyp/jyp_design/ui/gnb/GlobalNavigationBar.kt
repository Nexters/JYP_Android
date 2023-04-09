package com.jyp.jyp_design.ui.gnb

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors

enum class GlobalNavigationBarColor {
    BLACK, WHITE, GREY
}

@Composable
fun GlobalNavigationBar(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        titleSize: TextUnit = 20.sp,
        titleFontWeight: FontWeight = FontWeight.Medium,
        description: String = "",
        activeBack: Boolean = false,
        backAction: () -> Unit = {},
) {
    Row(
            modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(
                            when (color) {
                                GlobalNavigationBarColor.BLACK -> JypColors.Background_grey300
                                GlobalNavigationBarColor.WHITE -> JypColors.Background_white100
                                GlobalNavigationBarColor.GREY -> JypColors.Background_white200
                            }
                    ),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        
        if (activeBack) {
            Icon(
                    modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(onClick = backAction),
                    painter = painterResource(R.drawable.icon_left_arrow),
                    contentDescription = null,
                    tint = when (color) {
                        GlobalNavigationBarColor.BLACK -> JypColors.Text_white
                        GlobalNavigationBarColor.WHITE -> JypColors.Text90
                        GlobalNavigationBarColor.GREY -> JypColors.Text90
                    }
            )
        }

        Text(
                modifier = Modifier.padding(start = 12.dp),
                text = title,
                fontSize = titleSize,
                color = when (color) {
                    GlobalNavigationBarColor.BLACK -> JypColors.Text_white
                    GlobalNavigationBarColor.WHITE -> JypColors.Text90
                    GlobalNavigationBarColor.GREY -> JypColors.Text90
                },
                fontWeight = titleFontWeight,
        )

        Text(
                modifier = Modifier.padding(start = 12.dp),
                text = description,
                fontSize = 18.sp,
                color = JypColors.Tag_grey200,
                fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun GlobalNavigationBarLayout(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        titleSize: TextUnit = 20.sp,
        titleFontWeight: FontWeight = FontWeight.Medium,
        description: String = "",
        activeBack: Boolean = false,
        backAction: () -> Unit = {},
        contents: @Composable (modifier: Modifier) -> Unit,
) {
    Column(
            modifier = Modifier.fillMaxSize()
    ) {
        GlobalNavigationBar(
                color = color,
                title = title,
                titleSize = titleSize,
                titleFontWeight = titleFontWeight,
                description = description,
                activeBack = activeBack,
                backAction = backAction,
        )

        contents(Modifier.background(JypColors.Background_white100))
    }
}

@Composable
@Preview(showBackground = true)
internal fun GlobalNavigationBarBoldPreview() {
    GlobalNavigationBar(
            title = "BoldPreview",
            titleFontWeight = FontWeight.SemiBold,
            titleSize = 20.sp,
            description = "12m 25d",
            activeBack = true,
    )
}

@Composable
@Preview(showBackground = true)
internal fun GlobalNavigationBarMediumPreview() {
    GlobalNavigationBar(
            title = "MediumPreview",
            titleFontWeight = FontWeight.Normal,
            titleSize = 16.sp,
            activeBack = false,
    )
}
