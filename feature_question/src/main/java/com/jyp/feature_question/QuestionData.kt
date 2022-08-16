package com.jyp.feature_question

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes


data class QuestionData(
    val id: Int,
    @StringRes val titleRes: Int,
    @ArrayRes val optionsRes: Int,
    val images: List<Int>,
    var selectedIndex: Int?

)