package com.jyp.jyp_design.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.jyp.jyp_design.R

object JypPainter {
    val add: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_add)

    val check: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_check)

    val CulturalInstitution: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_cultural_institution)
}
