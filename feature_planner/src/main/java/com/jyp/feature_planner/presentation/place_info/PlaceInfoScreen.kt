package com.jyp.feature_planner.presentation.place_info

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun PlaceInfoScreen() {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { activity?.finish() }
            ) {
                Icon(
                    painter = painterResource(com.jyp.jyp_design.R.drawable.icon_close),
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
                    text = "아르떼 뮤지엄",
                    type = TextType.TITLE_3,
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = JypColors.Text80
                )

            }
        }

        val url = "https://place.map.kakao.com/m/2115731103"
        var backEnabled by remember { mutableStateOf(false) }
        var webView: WebView? = null
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
                            backEnabled = view.canGoBack()
                        }
                    }
                    settings.javaScriptEnabled = true

                    loadUrl(url)
                    webView = this
                }
            }, update = {
                webView = it
            })

        BackHandler(enabled = backEnabled) {
            webView?.goBack()
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun PlaceInfoScreenPreview() {
    PlaceInfoScreen()
}
