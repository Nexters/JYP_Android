package com.jyp.jyp_design.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText


@Composable
fun JypTextButton(
    text: String,
    buttonType: ButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    enabledColor: Color,
    shape: Shape = RoundedCornerShape(12.dp),
    border: BorderStroke? = null,
    elevation: ButtonElevation? = null,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        border = border,
        elevation = elevation,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (enabled) enabledColor else JypColors.Tag_white_grey100
        )
    ) {
        JypText(
            text = text,
            type = buttonType.textType,
            modifier = Modifier.padding(
                vertical = buttonType.verticalPadding.dp
            ),
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = if (enabled) JypColors.Text_white else JypColors.Text40
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun ThickButtonPreview() {
    JypTextButton(
        text = "ThickButton",
        buttonType = ButtonType.THICK,
        enabledColor = JypColors.Pink,
        onClick = {  }
    )
}

@Composable
@Preview(showBackground = true)
internal fun MediumButtonPreview() {
    JypTextButton(
        text = "MediumButton",
        buttonType = ButtonType.MEDIUM,
        enabledColor = JypColors.Pink,
        onClick = {  }
    )
}

@Composable
@Preview(showBackground = true)
internal fun ThinButtonPreview() {
    JypTextButton(
        text = "ThinButton",
        buttonType = ButtonType.THIN,
        enabledColor = JypColors.Pink,
        onClick = {  }
    )
}