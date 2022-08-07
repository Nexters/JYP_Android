package com.jyp.jyp_design.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.typography.style.bodyTextStyle
import com.jyp.jyp_design.ui.typography.type.BodyType


@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    type: BodyType,
    textAlign: TextAlign,
    color: Color
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = bodyTextStyle(
            type = type,
            textAlign = textAlign,
            color = color
        )
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_1() {
    BodyText(
        text = "BodyText",
        type = BodyType.BODY_1,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_2() {
    BodyText(
        text = "BodyText",
        type = BodyType.BODY_2,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_3() {
    BodyText(
        text = "BodyText",
        type = BodyType.BODY_3,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_4() {
    BodyText(
        text = "BodyText",
        type = BodyType.BODY_4,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}