package com.jyp.jyp_design.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.typography.style.TextStyle
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun Text(
    text: String,
    type: TextType,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    color: Color
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            type = type,
            textAlign = textAlign,
            color = color
        )
    )
}


@Composable
@Preview(showBackground = true)
internal fun HeadingText_1() {
    Text(
        text = "HeadingText_1",
        type = TextType.HEADING_1,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun HeadingText_2() {
    Text(
        text = "HeadingText_2",
        type = TextType.HEADING_2,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun HeadingText_3() {
    Text(
        text = "HeadingText_3",
        type = TextType.HEADING_3,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_1() {
    Text(
        text = "TitleText_1",
        type = TextType.TITLE_1,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_2() {
    Text(
        text = "TitleText_2",
        type = TextType.TITLE_2,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_3() {
    Text(
        text = "TitleText_3",
        type = TextType.TITLE_3,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_4() {
    Text(
        text = "TitleText_4",
        type = TextType.TITLE_4,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_5() {
    Text(
        text = "TitleText_5",
        type = TextType.TITLE_5,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TitleText_6() {
    Text(
        text = "TitleText_6",
        type = TextType.TITLE_6,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_1() {
    Text(
        text = "BodyText_1",
        type = TextType.BODY_1,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_2() {
    Text(
        text = "BodyText_2",
        type = TextType.BODY_2,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_3() {
    Text(
        text = "BodyText_3",
        type = TextType.BODY_3,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun BodyText_4() {
    Text(
        text = "BodyText_4",
        type = TextType.BODY_4,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TagText_1() {
    Text(
        text = "TagText_1",
        type = TextType.TAG_1,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TagText_2() {
    Text(
        text = "TagText_2",
        type = TextType.TAG_2,
        color = JypColors.Text90
    )
}