package com.jyp.jyp_design.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.ui.text.JypText


@Composable
fun JypTextButton(
    text: String,
    buttonType: ButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    border: BorderStroke? = null,
    buttonColorSet: ButtonColorSetType,
    elevation: ButtonElevation? = null,
    onClickEnabled: () -> Unit = {},
    onClickDisabled: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        border = border,
        elevation = elevation,
        onClick = when (enabled) {
            true -> onClickEnabled
            false -> onClickDisabled
        },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = buttonColorSet.backgroundColor
        ),
        contentPadding = PaddingValues(0.dp),
    ) {
        JypText(
            text = text,
            type = buttonType.textType,
            modifier = Modifier.padding(
                vertical = buttonType.verticalPadding.dp
            ),
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = buttonColorSet.textColor
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun ThickButtonPreview() {
    JypTextButton(
        text = "ThickButton",
        buttonType = ButtonType.THICK,
        enabled = true,
        buttonColorSet = ButtonColorSetType.PINK
    )
}

@Composable
@Preview(showBackground = true)
internal fun MediumButtonPreview() {
    JypTextButton(
        text = "MediumButton",
        buttonType = ButtonType.MEDIUM,
        enabled = true,
        buttonColorSet = ButtonColorSetType.BLACK
    )
}

@Composable
@Preview(showBackground = true)
internal fun ThinButtonPreview() {
    JypTextButton(
        text = "ThinButton",
        buttonType = ButtonType.THIN,
        enabled = true,
        buttonColorSet = ButtonColorSetType.YELLOW
    )
}
