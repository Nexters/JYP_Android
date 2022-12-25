package com.jyp.feature_question.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.jyp.feature_question.R
import com.jyp.jyp_design.resource.JypPainter


enum class QuestionEnum(
    val index: Int,
    val titleRes: Int,
    val optionsRes: Int
) {
    QUESTION_01(
        index = 0,
        titleRes = R.string.question01,
        optionsRes = R.array.question01_options
    ) {
        @Composable
        override fun getOptionPainterList(): List<Painter> {
            return listOf(
                JypPainter.question01Illustration01,
                JypPainter.question01Illustration02
            )
        }
    },
    QUESTION_02(
        index = 1,
        titleRes = R.string.question02,
        optionsRes = R.array.question02_options,
    ) {
        @Composable
        override fun getOptionPainterList(): List<Painter> {
            return listOf(
                JypPainter.question02Illustration01,
                JypPainter.question02Illustration02
            )
        }
    },
    QUESTION_03(
        index = 2,
        titleRes = R.string.question03,
        optionsRes = R.array.question03_options,
    ) {
        @Composable
        override fun getOptionPainterList(): List<Painter> {
            return listOf(
                JypPainter.question03Illustration01,
                JypPainter.question03Illustration02
            )
        }
    };

    @Composable
    abstract fun getOptionPainterList(): List<Painter>
}