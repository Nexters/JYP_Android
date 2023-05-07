package com.jyp.jyp_design.ui.gnb

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

enum class GlobalNavigationBarColor {
    BLACK, WHITE, GREY
}

@Composable
fun GlobalNavigationBar(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        textType: TextType,
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

        JypText(
                modifier = Modifier.padding(start = 12.dp),
                text = title,
                type = textType,
                color = when (color) {
                    GlobalNavigationBarColor.BLACK -> JypColors.Text_white
                    GlobalNavigationBarColor.WHITE -> JypColors.Text90
                    GlobalNavigationBarColor.GREY -> JypColors.Text90
                }
        )

        JypText(
                modifier = Modifier.padding(start = 12.dp),
                text = description,
                type = TextType.TITLE_4,
                color = JypColors.Tag_grey200
        )
    }
}

@Composable
fun GlobalNavigationBarLayout(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        textType: TextType,
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
                textType = textType,
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
            textType = TextType.HEADING_2,
            description = "12m 25d",
            activeBack = true,
    )
}

@Composable
@Preview(showBackground = true)
internal fun GlobalNavigationBarMediumPreview() {
    GlobalNavigationBar(
            title = "MediumPreview",
            textType = TextType.BODY_2,
            activeBack = false,
    )
}
