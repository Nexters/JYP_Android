package com.jyp.feature_my_page.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun AppInfoScreen() {

    var webView: WebView? = null
    var isWebViewBackEventEnable by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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

                    loadUrl("https://plausible-seahorse-ba5.notion.site/Journey-piki-688aac1424924a30bf974c7866592a98")
                    webView = this
                }
            },
            update = {
                webView = it
            }
        )

        BackHandler(enabled = isWebViewBackEventEnable) {
            webView?.goBack()
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun AppInfoScreenPreview() {
    AppInfoScreen()
}