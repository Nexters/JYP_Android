package com.jyp.jyp_design.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.typography.style.headingTextStyle
import com.jyp.jyp_design.ui.typography.type.HeadingType


@Composable
internal fun HeadingText(
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    type: HeadingType,
    textAlign: TextAlign,
    color: Color
) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = headingTextStyle(
            type = type,
            textAlign = textAlign,
            color = color
        )
    )
}

@Composable
@Preview(showBackground = true)
internal fun HeadingText_1() {
    HeadingText(
        text = "HeadingText",
        type = HeadingType.HEADING_1,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun HeadingText_2() {
    HeadingText(
        text = "HeadingText",
        type = HeadingType.HEADING_2,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun HeadingText_3() {
    HeadingText(
        text = "HeadingText",
        type = HeadingType.HEADING_3,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}