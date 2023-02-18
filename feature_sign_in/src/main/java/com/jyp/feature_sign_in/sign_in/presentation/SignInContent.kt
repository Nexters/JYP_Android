package com.jyp.feature_sign_in.sign_in.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_sign_in.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun SignInContent(
    onClickSignInButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white100),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 52.dp)
                .background(
                    color = JypColors.Pink,
                    shape = RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = JypPainter.journeyPikiApp,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .wrapContentHeight()
            )
        }
        JypText(
            text = stringResource(id = R.string.sign_in_description),
            type = TextType.BODY_1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 16.dp)
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = JypColors.Background_grey300
        )
        Image(
            painter = painterResource(id = R.drawable.button_kakao_sign_in),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp)
                .padding(horizontal = 24.dp)
                .clickable { onClickSignInButton() },
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
@Preview(showBackground = true)
internal fun SignInContentPreview() {
    SignInContent(
        onClickSignInButton = {}
    )
}