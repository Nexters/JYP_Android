package com.jyp.feature_my_journey.presentation.my_journey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JoinJourneyBottomSheetScreen(
    onClickCancelButton: () -> Unit,
    onClickNextButton: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var joinCodeFromTextInput by remember { mutableStateOf("") }

    val keyboard = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp,
                bottom = 34.dp
            )
    ) {
        Header(onClickCancelButton = {
            onClickCancelButton()
            keyboard?.hide()
        })
        Spacer(modifier = Modifier.size(32.dp))
        Content(
            modifier = Modifier.weight(1f),
            keyboard = keyboard,
            focusRequester = focusRequester,
            joinCodeFromTextInput = joinCodeFromTextInput,
            onJoinCodeChanged = {
                joinCodeFromTextInput = it
                if (joinCodeFromTextInput.isBlank()) {
                    focusRequester.requestFocus()
                    keyboard?.show()
                }
            },
            onClickJoinCodeFromClipboard = { joinCodeFromTextInput = it },
        )
        JypTextButton(
            text = stringResource(id = com.jyp.jyp_design.R.string.button_next),
            buttonType = ButtonType.THICK,
            modifier = Modifier.fillMaxWidth(),
            buttonColorSet = ButtonColorSetType.PINK,
            enabled = joinCodeFromTextInput.isNotBlank(),
            onClickEnabled = { onClickNextButton(joinCodeFromTextInput) }
        )
    }
}

@Composable
private fun Header(
    onClickCancelButton: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JypText(
            text = "플래너 입장하기",
            type = TextType.TITLE_2,
            color = JypColors.Text80,
        )
        JypText(
            text = "취소",
            type = TextType.BODY_2,
            color = JypColors.Text40,
            modifier = Modifier.clickable {
                onClickCancelButton()
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Content(
    modifier: Modifier,
    keyboard: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    joinCodeFromTextInput: String,
    onJoinCodeChanged: (String) -> Unit,
    onClickJoinCodeFromClipboard: (String) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val joinCodeFromClipboard = clipboardManager.getText()?.text


    Column(
        modifier = modifier
    ) {
        JypText(
            text = "참여 코드",
            type = TextType.BODY_3,
            color = JypColors.Text75
        )
        Spacer(modifier = Modifier.height(10.dp))
        JypTextInput(
            type = TextInputType.FIELD,
            text = joinCodeFromTextInput,
            modifier = Modifier
                .fillMaxWidth(1f)
                .focusRequester(focusRequester),
            valueChange = { onJoinCodeChanged(it) },
            hint = "입력해주세요",
        )
        Spacer(modifier = Modifier.height(16.dp))
        joinCodeFromClipboard?.let {
            JypText(
                text = when (it.length > 8) {
                    true -> it.substring(0..7) + "…"
                    false -> it
                },
                type = TextType.TAG_1,
                modifier = Modifier
                    .background(
                        color = JypColors.Tag_white_blue100,
                        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                    .clickable(
                        enabled = it.isNotBlank(),
                        onClick = {
                            onClickJoinCodeFromClipboard(it)
                            keyboard?.hide()
                        }
                    ),
                color = JypColors.Sub_blue300
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@Preview(showBackground = true)
@Composable
fun JoinJourneyBottomSheetScreenPreview() {
    JoinJourneyBottomSheetScreen(
        onClickCancelButton = {},
        onClickNextButton = {}
    )
}
