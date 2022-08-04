package com.jyp.jyp_design.ui.shadow

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.drawShadow(
        color: Color = Color.Black,
        alpha: Float = 0.9f,
        borderRadius: Dp = 10.dp,
        offsetX: Dp = 1.dp,
        offsetY: Dp = 2.5.dp,
        blurRadius: Dp = 3.dp,
        enabled: Boolean = true,
) = if (enabled) {
    this.drawBehind {
        val transparentColor = color.copy(alpha = 0.0f).toArgb()
        val shadowColor = color.copy(alpha = alpha).toArgb()
        this.drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                    blurRadius.toPx(),
                    offsetX.toPx(),
                    offsetY.toPx(),
                    shadowColor
            )
            canvas.save()

            canvas.scale(
                    1f,
                    1f,
                    this.center.x,
                    this.center.y
            )

            canvas.drawRoundRect(
                    0f,
                    0f,
                    this.size.width,
                    this.size.height,
                    borderRadius.toPx(),
                    borderRadius.toPx(),
                    paint
            )
            canvas.restore()
        }
    }
} else {
    this
}
