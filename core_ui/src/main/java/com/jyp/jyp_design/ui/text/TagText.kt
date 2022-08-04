package com.jyp.jyp_design.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.typography.style.tagTextStyle
import com.jyp.jyp_design.ui.typography.type.TagType


@Composable
fun TagText(
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    type: TagType,
    textAlign: TextAlign,
    color: Color
) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = tagTextStyle(
            type = type,
            textAlign = textAlign,
            color = color
        )
    )
}

@Composable
@Preview(showBackground = true)
internal fun TagText_1() {
    TagText(
        text = "TagText",
        type = TagType.TAG_1,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}

@Composable
@Preview(showBackground = true)
internal fun TagText_2() {
    TagText(
        text = "TagText",
        type = TagType.TAG_2,
        textAlign = TextAlign.Center,
        color = JypColors.Text90
    )
}