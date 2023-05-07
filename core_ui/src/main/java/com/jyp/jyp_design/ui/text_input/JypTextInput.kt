package com.jyp.jyp_design.ui.text_input

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun JypTextInput(
    modifier: Modifier = Modifier,
    type: TextInputType,
    text: String = "",
    valueChange: (String) -> Unit = {},
    hint: String = "",
    trailingImage: Painter? = null,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true
) {
    when (type) {
        TextInputType.FIELD -> {
            TextInputField(
                modifier = modifier,
                text = text,
                valueChange = valueChange,
                hint = hint,
                trailingImage = trailingImage,
                maxLines = maxLines,
                singleLine = singleLine,
            )
        }
        TextInputType.BOX -> {
            TextInputBox(
                modifier = modifier,
                text = text,
                valueChange = valueChange,
                hint = hint,
                trailingImage = trailingImage,
                maxLines = maxLines,
                singleLine = singleLine,
            )
        }
    }
}

@Composable
internal fun TextInputField(
    modifier: Modifier = Modifier,
    text: String,
    valueChange: (String) -> Unit,
    hint: String,
    trailingImage: Painter? = null,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
) {
    val textFieldFocus = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
                drawLine(
                    color = JypColors.Black20,
                    strokeWidth = 1f,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                )
            }
            .padding(
                vertical = 4.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextInputContent(
            modifier = Modifier.weight(1f),
            text = text,
            textType = TextType.TITLE_1,
            valueChange = valueChange,
            hint = hint,
            textFieldFocus = textFieldFocus.value,
            textFieldFocusChange = {
                textFieldFocus.value = it
            },
            maxLines = maxLines,
            singleLine = singleLine,
        )

        if (text.isEmpty() || (text.isNotEmpty() && !textFieldFocus.value)) {
            trailingImage?.let { painter ->
                Image(
                    painter = painter,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
internal fun TextInputBox(
    modifier: Modifier = Modifier,
    text: String,
    valueChange: (String) -> Unit,
    hint: String,
    trailingImage: Painter? = null,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
) {
    val textFieldFocus = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(JypColors.Background_white200)
            .clip(RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(1.dp, JypColors.Border_grey),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextInputContent(
            modifier = Modifier.weight(1f),
            text = text,
            textType = TextType.TITLE_3,
            valueChange = valueChange,
            hint = hint,
            textFieldFocus = textFieldFocus.value,
            textFieldFocusChange = {
                textFieldFocus.value = it
            },
            maxLines = maxLines,
            singleLine = singleLine,
        )

        if (text.isEmpty() || (text.isNotEmpty() && !textFieldFocus.value)) {
            trailingImage?.let { painter ->
                Image(
                    painter = painter,
                    contentDescription = null,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TextInputContent(
    modifier: Modifier = Modifier,
    text: String,
    textType: TextType,
    valueChange: (String) -> Unit,
    hint: String,
    textFieldFocus: Boolean,
    textFieldFocusChange: (Boolean) -> Unit = {},
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    textFieldFocusChange.invoke(focusState.hasFocus)
                },
            value = text,
            onValueChange = valueChange,
            textStyle = TextStyle(
                color = JypColors.Text90,
                fontSize = textType.fontSize.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            cursorBrush = SolidColor(JypColors.Text90),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    JypText(
                        text = hint,
                        type = textType,
                        color = JypColors.Text40
                    )
                }

                innerTextField()
            },
            maxLines = maxLines,
            singleLine = singleLine,
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )

        if (text.isNotEmpty() && textFieldFocus) {
            Image(
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    ) {
                        valueChange.invoke("")
                    },
                painter = painterResource(id = R.drawable.ic_text_delete),
                contentDescription = null,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun TextInputFieldPreview() {
    JypTextInput(
        type = TextInputType.FIELD,
        text = "아르떼 뮤지엄",
        valueChange = {},
        hint = "aa",
        trailingImage = painterResource(id = R.drawable.ic_search),
    )
}

@Composable
@Preview(showBackground = true)
internal fun TextInputFieldHintPreview() {
    JypTextInput(
        type = TextInputType.FIELD,
        text = "",
        valueChange = {},
        hint = "예) 서울 저니 식당",
        trailingImage = painterResource(id = R.drawable.ic_search),
    )
}

@Composable
@Preview(showBackground = true)
internal fun TextInputBoxPreview() {
    JypTextInput(
        type = TextInputType.BOX,
        text = "아르떼 뮤지엄",
        valueChange = {},
        hint = "aa",
        trailingImage = painterResource(id = R.drawable.ic_search),
    )
}

@Composable
@Preview(showBackground = true)
internal fun TextInputBoxHintPreview() {
    JypTextInput(
        type = TextInputType.BOX,
        text = "",
        valueChange = {},
        hint = "예) 서울 저니 식당",
        trailingImage = painterResource(id = R.drawable.ic_search),
    )
}
