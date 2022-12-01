package com.jyp.feature_add_place.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.Job


@Composable
fun SearchPlaceScreen(
    apiState: ApiState,
    onPlaceNameChanged: (String) -> Unit,
    onClickPlaceItem: (SearchPlaceResultModel) -> Unit,
    onClickBackButton: () -> Unit
) {
    var placeName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white100)
    ) {
        SearchPlaceHeader(
            placeName = placeName,
            onPlaceNameChanged = {
                placeName = it
                onPlaceNameChanged(placeName)
            },
            onClickBackButton = onClickBackButton,
        )
        when (placeName.isBlank()) {
            true -> SearchPlaceGuideView()
            false -> SearchPlaceResultView(
                apiState,
                onClickPlaceItem
            )
        }
    }
}

@Composable
fun SearchPlaceHeader(
    placeName: String,
    onPlaceNameChanged: (String) -> Unit,
    onClickBackButton: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(JypColors.Background_white100)
            .padding(start = 12.dp)
            .padding(end = 24.dp)
            .padding(vertical = 12.dp)
    ) {
        IconButton(
            onClick = { onClickBackButton() },
            enabled = true
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_left_arrow),
                contentDescription = null
            )
        }
        JypTextInput(
            modifier = Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .align(Alignment.CenterVertically)
                .focusRequester(focusRequester),
            type = TextInputType.BOX,
            text = placeName,
            valueChange = { onPlaceNameChanged(it) },
            hint = "예) 서울 저니 식당",
            trailingImage = painterResource(id = R.drawable.ic_search),
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun SearchPlaceGuideView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = JypPainter.searchPlaceGuide,
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .wrapContentWidth()
        )
        JypText(
            text = stringResource(com.jyp.feature_add_place.R.string.request_search_place),
            type = TextType.BODY_2,
            color = JypColors.Text75
        )
    }
}

@Composable
fun SearchPlaceResultView(
    apiState: ApiState,
    onClickPlaceItem: (SearchPlaceResultModel) -> Unit,
) {
    when (apiState) {
        is ApiState.Loading -> return
        is ApiState.Success -> {
            when (apiState.searchPlaceResult.isEmpty()) {
                true -> SearchPlaceEmptyView()
                false -> SearchPlaceListView(
                    apiState.searchPlaceResult,
                    onClickPlaceItem
                )
            }
        }
        is ApiState.Failure -> SearchPlaceEmptyView()
    }
}

@Composable
fun SearchPlaceEmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = JypPainter.searchPlaceEmpty,
            contentDescription = null,
            modifier = Modifier
                .height(108.dp)
                .wrapContentWidth()
        )
        JypText(
            text = stringResource(com.jyp.feature_add_place.R.string.search_place_empty),
            type = TextType.BODY_2,
            color = JypColors.Text75
        )
    }
}

@Composable
fun SearchPlaceListView(
    SearchPlaceResults: List<SearchPlaceResultModel>,
    onClickPlaceItem: (SearchPlaceResultModel) -> Unit,
) {
    LazyColumn {
        itemsIndexed(items = SearchPlaceResults) { _, item ->
            SearchPlaceResultItem(
                placeResult = item,
                onClickPlaceItem = onClickPlaceItem
            )
        }
    }
}

@Composable
fun SearchPlaceResultItem(
    placeResult: SearchPlaceResultModel,
    onClickPlaceItem: (SearchPlaceResultModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 12.dp)
            .clickable { onClickPlaceItem(placeResult) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(start = 32.dp)
            ) {
                JypText(
                    text = placeResult.name,
                    type = TextType.TITLE_5,
                    modifier = Modifier.padding(bottom = 2.dp),
                    color = JypColors.Text80
                )
                JypText(
                    text = placeResult.address,
                    type = TextType.BODY_4,
                    color = JypColors.Text40
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Image(
                    painter = painterResource(placeResult.categoryEnum.categoryIconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(30.dp)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 2.dp)
                )
                JypText(
                    text = stringResource(placeResult.categoryEnum.journeyPikiCategoryNameRes),
                    type = TextType.BODY_4,
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center,
                    color = JypColors.Text75
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            color = JypColors.Black10,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPlaceScreenPreview() {
    SearchPlaceScreen(
        apiState = ApiState.Success(emptyList()),
        onPlaceNameChanged = { Job() },
        onClickPlaceItem = {},
        onClickBackButton = {}
    )
}