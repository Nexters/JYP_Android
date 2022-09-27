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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.util.JourneyPikiPlaceCategoryEnum
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun PlaceInfoScreen(
    searchPlaceResultModel: SearchPlaceResultModel,
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
                    painter = painterResource(R.drawable.icon_close),
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
                    text = searchPlaceResultModel.name,
                    type = TextType.TITLE_3,
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = JypColors.Text80
                )

            }
        }

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

                    loadUrl(searchPlaceResultModel.infoUrl)
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
    PlaceInfoScreen(
        searchPlaceResultModel = SearchPlaceResultModel(
            name = "",
            address = "",
            categoryEnum = JourneyPikiPlaceCategoryEnum.CAFE,
            x = 0.0,
            y = 0.0,
            infoUrl = "https://place.map.kakao.com/m/2115731103"
        ),
        onClickCloseButton = {}
    )
}
