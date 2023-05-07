package com.jyp.jyp_design.ui.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun PlaceInfoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = DefaultShadowColor.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(JypColors.Background_white100)
            .padding(
                horizontal = 8.dp,
                vertical = 6.dp
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = JypPainter.eyes,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(4.dp))
        JypText(
            modifier = Modifier.wrapContentSize(),
            text = "정보 보기",
            type = TextType.BODY_1,
            color = JypColors.Text80
        )
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlaceInfoButtonPreview() {
    PlaceInfoButton(
        modifier = Modifier,
        onClick = {}
    )
}