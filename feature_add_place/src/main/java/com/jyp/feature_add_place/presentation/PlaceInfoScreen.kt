package com.jyp.feature_add_place.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun PlaceInfoScreen(
    placeInfoName: String,
    placeInfoUrl: String,
    onClickCloseButton: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(color = JypColors.Background_white100),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onClickCloseButton() }
            ) {
                Icon(
                    painter = JypPainter.closeSharp,
                    contentDescription = null,
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 14.dp
                    ),
                    tint = JypColors.Sub_black
                )
            }
            Row(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JypText(
                    text = placeInfoName,
                    type = TextType.TITLE_3,
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = JypColors.Text80
                )

            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = JypColors.Black15)
        )

        var webView: WebView? = null
        var isWebViewBackEventEnable by remember { mutableStateOf(false) }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            isWebViewBackEventEnable = view.canGoBack()
                        }
                    }
                    settings.javaScriptEnabled = true

                    loadUrl(placeInfoUrl)
                    webView = this
                }
            }, update = {
                webView = it
            })

        BackHandler(enabled = isWebViewBackEventEnable) {
            webView?.goBack()
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlaceInfoScreenPreview() {
    PlaceInfoScreen(
        placeInfoName = "",
        placeInfoUrl = "https://place.map.kakao.com/m/2115731103",
        onClickCloseButton = {}
    )
}
