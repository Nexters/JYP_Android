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
import com.jyp.feature_add_place.util.getJourneyPikiPlaceCategoryEnum
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
fun PlaceSearchScreen(
    onClickBackButton: () -> Unit
) {
    var place by remember { mutableStateOf("") }
//    val placeSearchResults = remember { emptyList<PlaceModel>() }
//    val placeSearchResults = remember { SearchPlaceResultProvider.placeList }
//    val placeSearchResults = remember { SearchPlaceResultProvider.placeList }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JypColors.Background_white100)
    ) {
        PlaceSearchHeader(
            place = place,
            onValueChanged = { place = it },
            onClickBackButton = onClickBackButton,
        )
        when (place.isBlank()) {
            true -> RequestPlaceSearchView()
            false -> {

                when (placeSearchResults.isEmpty()) {
                    true -> PlaceSearchEmptyView()
                    false -> PlaceSearchResultView(placeSearchResults)
                }
            }
        }
    }
}

@Composable
fun PlaceSearchHeader(
    place: String,
    onValueChanged: (String) -> Unit,
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
            text = place,
            valueChange = { onValueChanged(it) },
            hint = "예) 서울 저니 식당",
            trailingImage = painterResource(id = R.drawable.ic_search),
        )
    }
}

@Composable
fun RequestPlaceSearchView() {
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
fun PlaceSearchEmptyView() {
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
fun PlaceSearchResultView(
    placeSearchResults: List<PlaceModel>
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 6.dp
        )
    ) {
        itemsIndexed(items = placeSearchResults) { index, item ->
            PlaceSearchResultItem(place = item)
        }
    }
}

@Composable
fun PlaceSearchResultItem(
    place: PlaceModel
) {
    val placeCategoryEnum = place.category.getJourneyPikiPlaceCategoryEnum()

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
                text = place.name,
                type = TextType.TITLE_5,
                color = JypColors.Text80
            )
            JypText(
                text = place.address,
                type = TextType.BODY_4,
                color = JypColors.Text40
            )
        }
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(placeCategoryEnum.categoryIconRes),
                contentDescription = null
            )
            JypText(
                text = stringResource(placeCategoryEnum.journeyPikiCategoryNameRes),
                type = TextType.BODY_4,
                color = JypColors.Text75
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceSearchScreenPreview() {
    PlaceSearchScreen(
        onClickBackButton = { }
    )
}