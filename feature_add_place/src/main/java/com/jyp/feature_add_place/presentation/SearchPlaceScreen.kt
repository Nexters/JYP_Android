package com.jyp.feature_add_place.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.feature_add_place.util.getJourneyPikiPlaceCategoryEnum
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType
import kotlinx.coroutines.Job


@Composable
fun SearchPlaceScreen(
    uiState: UiState,
    onPlaceNameChanged: (String) -> Unit,
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
            true -> RequestSearchPlaceView()
            false -> SearchPlaceResultWithApiResponse(uiState)
        }
    }
}

@Composable
fun SearchPlaceHeader(
    placeName: String,
    onPlaceNameChanged: (String) -> Unit,
    onClickBackButton: () -> Unit
) {
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
                .align(Alignment.CenterVertically),
            type = TextInputType.BOX,
            text = placeName,
            valueChange = { onPlaceNameChanged(it) },
            hint = "예) 서울 저니 식당",
            trailingImage = painterResource(id = R.drawable.ic_search),
        )
    }
}

@Composable
fun RequestSearchPlaceView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(com.jyp.feature_add_place.R.drawable.icon_search_place),
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
fun SearchPlaceResultWithApiResponse(uiState: UiState) {
    when (uiState) {
        is UiState.Loading -> return
        is UiState.Success -> {
            when (uiState.searchPlaceResult.isEmpty()) {
                true -> SearchPlaceEmptyView()
                false -> SearchPlaceResultView(uiState.searchPlaceResult)
            }
        }
        is UiState.Failure -> SearchPlaceEmptyView()
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
            painter = painterResource(com.jyp.feature_add_place.R.drawable.icon_search_place_empty),
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
fun SearchPlaceResultView(
    SearchPlaceResults: List<SearchPlaceResultModel>
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 6.dp
        )
    ) {
        itemsIndexed(items = SearchPlaceResults) { index, item ->
            SearchPlaceResultItem(placeResult = item)
        }
    }
}

@Composable
fun SearchPlaceResultItem(
    placeResult: SearchPlaceResultModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
        ) {
            JypText(
                text = placeResult.name,
                type = TextType.TITLE_5,
                color = JypColors.Text80
            )
            JypText(
                text = placeResult.address,
                type = TextType.BODY_4,
                color = JypColors.Text40
            )
        }
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(placeResult.categoryEnum.categoryIconRes),
                contentDescription = null
            )
            JypText(
                text = stringResource(placeResult.categoryEnum.journeyPikiCategoryNameRes),
                type = TextType.BODY_4,
                color = JypColors.Text75
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPlaceScreenPreview() {
    SearchPlaceScreen(
        uiState = UiState.Success(emptyList()),
        onPlaceNameChanged = { Job() },
        onClickBackButton = { }
    )
}