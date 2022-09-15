package com.jyp.feature_add_place

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType


@Composable
fun PlaceSearchScreen(
    onClickBackButton: () -> Unit
) {
    var place by remember { mutableStateOf("") }

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

@Preview(showBackground = true)
@Composable
fun PlaceSearchScreenPreview() {
    PlaceSearchScreen(
        onClickBackButton = { }
    )
}