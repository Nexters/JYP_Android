package com.jyp.jyp_design.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.typography.style.titleTextStyle
import com.jyp.jyp_design.ui.typography.type.TitleType


@Composable
internal fun TitleText(
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    type: TitleType,
    textAlign: TextAlign,
    color: Color
) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = titleTextStyle(
            type = type,
            textAlign = textAlign,
            color = color
        )
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_1() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_1,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_2() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_2,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_3() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_3,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_4() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_4,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_5() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_5,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_6() {
    TitleText(
        text = "TitleText",
        type = TitleType.TITLE_6,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}