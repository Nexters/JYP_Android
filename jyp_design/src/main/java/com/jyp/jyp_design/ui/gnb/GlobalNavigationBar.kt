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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors

enum class GlobalNavigationBarColor {
    BLACK, WHITE
}

@Composable
fun GlobalNavigationBar(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        tag: String? = null,
        activeBack: Boolean = false,
        backAction: () -> Unit = {},
) {
    Row(
            modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(
                            when (color) {
                                GlobalNavigationBarColor.BLACK -> JypColors.background_grey300
                                GlobalNavigationBarColor.WHITE -> JypColors.background_white100
                            }
                    ),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        if (activeBack) {
            Icon(
                    modifier = Modifier
                            .padding(start = 24.dp)
                            .clickable(onClick = backAction),
                    painter = painterResource(R.drawable.icon_left_arrow),
                    contentDescription = null,
            )
        }

        Spacer(
                modifier = Modifier.size(12.dp)
        )

        if (tag != null) {
            GlobalNavigationBarTextBold(
                    title = title,
                    tag = tag,
            )
        } else {
            GlobalNavigationBarTextMedium(title = title)
        }
    }
}

@Composable
internal fun GlobalNavigationBarTextMedium(
        title: String,
) {
    Text(
            text = title,
            fontSize = 16.sp,
            color = JypColors.text75,
            fontWeight = FontWeight.Medium,
    )
}

@Composable
internal fun GlobalNavigationBarTextBold(
        title: String,
        tag: String,
) {
    Text(
            text = title,
            fontSize = 20.sp,
            color = JypColors.text90,
            fontWeight = FontWeight.SemiBold,
    )
    Text(
            modifier = Modifier.padding(start = 12.dp),
            text = tag,
            fontSize = 18.sp,
            color = JypColors.tag_grey200,
            fontWeight = FontWeight.Medium,
    )
}

@Composable
fun GlobalNavigationBarLayout(
        color: GlobalNavigationBarColor = GlobalNavigationBarColor.WHITE,
        title: String = "",
        tag: String? = null,
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
                tag = tag,
                activeBack = activeBack,
                backAction = backAction,
        )

        contents(Modifier.background(JypColors.background_white100))
    }
}

@Composable
@Preview(showBackground = true)
internal fun GlobalNavigationBarBoldPreview() {
    GlobalNavigationBar(
            title = "BoldPreview",
            tag = "12m 25d",
            activeBack = true,
    )
}

@Composable
@Preview(showBackground = true)
internal fun GlobalNavigationBarMediumPreview() {
    GlobalNavigationBar(
            title = "MediumPreview",
            tag = "12m 25d",
            activeBack = true,
    )
}
