package com.jyp.jyp_design.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.jyp.jyp_design.R


object JypPainter {
    val journeyPikiApp: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_journey_piki_app)

    val add: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_add)

    val check: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_check)

    val eyes: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_eyes)

    val closeSharp: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_close_sharp)

    val searchPlaceGuide: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_search_place_guide)

    val searchPlaceEmpty: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_search_place_empty)

    val CulturalInstitution: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_cultural_institution)

    // Question
    val question: Painter
        @Composable
        get() = painterResource(id = R.drawable.icon_question)

    val question01Illustration01: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question01_01)
    val question01Illustration02: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question01_02)

    val question02Illustration01: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question02_01)
    val question02Illustration02: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question02_02)

    val question03Illustration01: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question03_01)
    val question03Illustration02: Painter
        @Composable
        get() = painterResource(id = R.drawable.illust_question01_02)

    val invitationGuide01: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_invitation_01)
    val invitationGuide02: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_invitation_02)
    val invitationGuide03: Painter
        @Composable
        get() = painterResource(id = R.drawable.image_invitation_03)
}
