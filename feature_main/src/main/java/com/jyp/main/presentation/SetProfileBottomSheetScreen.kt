package com.jyp.main.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.*
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import com.jyp.main.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SelectProfileScreen(
        name: String,
        personality: String,
        selectedPosition: Int?,
        showDim: Boolean,
        onSelectProfile: (Int) -> Unit,
        submitProfile: () -> Unit,
) {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .let {
                        if (showDim) {
                            it.background(JypColors.Background_dim)
                        } else {
                            it
                        }
                    }
                    .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {}
                    ),
    ) {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(JypColors.Background_white100)
                        .padding(horizontal = 24.dp, vertical = 28.dp)
                        .align(Alignment.BottomCenter),
        ) {
            Header(
                    name = name,
                    personality = personality,
            )
            Spacer(modifier = Modifier.size(30.dp))
            Content(
                    name = name,
                    selectedPosition = selectedPosition,
                    onSelectProfile = onSelectProfile,
            )
            Spacer(modifier = Modifier.size(35.dp))
            JypTextButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "시작하기",
                    buttonType = ButtonType.THICK,
                    buttonColorSet = ButtonColorSetType.PINK,
                    enabled = selectedPosition != null,
                    onClickEnabled = submitProfile,
            )
        }
    }
}

@Composable
private fun Header(
        name: String,
        personality: String,
) {
    Column {
        JypText(
                text = "${name}님은\n${personality}이군요!",
                type = TextType.HEADING_1,
                color = JypColors.Text90,
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = "사용할 프로필을 선택해주세요",
                type = TextType.BODY_2,
                color = JypColors.Text40,
        )
    }
}

@Composable
private fun Content(
        name: String,
        selectedPosition: Int?,
        onSelectProfile: (Int) -> Unit,
) {
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
    ) {
        Profile(
                name = name,
                onSelectProfile = onSelectProfile,
                index = 0,
                isSelected = selectedPosition == 0,
        )
        Spacer(modifier = Modifier.size(64.dp))
        Profile(
                name = name,
                onSelectProfile = onSelectProfile,
                index = 1,
                isSelected = selectedPosition == 1,
        )
    }
}

@Composable
private fun Profile(
        name: String,
        onSelectProfile: (Int) -> Unit,
        index: Int,
        isSelected: Boolean,
) {
    Box(
            modifier = Modifier
                    .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onSelectProfile.invoke(index)
                            },
                    ),
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (index == 0) {
                GlideImage(
                        modifier = Modifier
                                .padding(top = 12.dp, start = 11.dp, end = 11.dp)
                                .size(70.dp)
                                .clip(RoundedCornerShape(20.dp)),
                        imageModel = "https://m.sleepnsleepmall.com/web/product/medium/202207/be10cef08bff16ef03355be34c8b73e6.jpg",
                        previewPlaceholder = R.drawable.group,
                )
            } else {
                Image(
                        modifier = Modifier
                                .padding(top = 12.dp, start = 11.dp, end = 11.dp)
                                .size(70.dp)
                                .clip(RoundedCornerShape(20.dp)),
                        painter = painterResource(id = R.drawable.group),
                        contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            JypText(
                    text = name.takeIf { it.length >= 4 }
                            ?.let { "${it.substring(0..2)}..." }
                            ?: name,
                    type = TextType.BODY_3,
                    color = JypColors.Text75,
            )
        }
        if (isSelected) {
            Image(
                    modifier = Modifier
                            .align(Alignment.TopEnd),
                    painter = JypPainter.check,
                    contentDescription = null,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SetProfileBottomSheetPreview() {
    SelectProfileScreen(
            name = "홍길동",
            personality = "자유로운 탐험가",
            onSelectProfile = {},
            selectedPosition = null,
            submitProfile = {},
            showDim = true,
    )
}

@Composable
@Preview(showBackground = true)
private fun SetProfileBottomSheetOverflowNamePreview() {
    SelectProfileScreen(
            name = "김수완무",
            personality = "까탈스러운 탐험가",
            onSelectProfile = {},
            selectedPosition = 1,
            submitProfile = {},
            showDim = true,
    )
}
