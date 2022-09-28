package com.jyp.journeypiki

import com.jyp.journeypiki.R


enum class OnboardingEnum(
    val titleRes: Int,
    val imageRes: Int
) {
    ONBOARDING_01(
        titleRes = R.string.onboarding_title01,
        imageRes = R.drawable.image_onboarding01
    ),
    ONBOARDING_02(
        titleRes = R.string.onboarding_title02,
        imageRes = R.drawable.image_onboarding02
    )
}