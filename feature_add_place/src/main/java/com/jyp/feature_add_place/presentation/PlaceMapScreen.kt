package com.jyp.feature_add_place.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jyp.feature_add_place.R
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.resource.JypPainter
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType


@Composable
internal fun PlaceMapScreen(
    searchPlaceResultModel: SearchPlaceResultModel,
    onClickBackButton: () -> Unit,
    onClickClearButton: () -> Unit,
    onClickInfoButton: () -> Unit,
    onClickAddPlaceButton: () -> Unit,
) {
    val searchPlaceResultLatLng = LatLng(
        searchPlaceResultModel.latitude,
        searchPlaceResultModel.longitude
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(searchPlaceResultLatLng, 15f)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ComposableAppBar(
            placeName = searchPlaceResultModel.name,
            onClickBackButton = { onClickBackButton() },
            onClickClearButton = { onClickClearButton() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    indoorLevelPickerEnabled = false,
                    mapToolbarEnabled = false,
                    myLocationButtonEnabled = false,
                    rotationGesturesEnabled = false,
                    scrollGesturesEnabled = false,
                    scrollGesturesEnabledDuringRotateOrZoom = false,
                    tiltGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = false
                )
            ) {
                Marker(
                    state = MarkerState(position = searchPlaceResultLatLng),
                    title = searchPlaceResultModel.name
//                    , snippet = "Marker in Singapore"
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(
                        color = JypColors.Background_white100,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            )
        }

        ComposablePlaceInfoBottomSheet(
            placeName = searchPlaceResultModel.name,
            placeAddress = searchPlaceResultModel.address,
            onClickInfoButton = { onClickInfoButton() },
            onClickAddPlaceButton = onClickAddPlaceButton,
        )
    }
}

@Composable
private fun ComposableAppBar(
    placeName: String,
    onClickBackButton: () -> Unit,
    onClickClearButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(JypColors.Background_white100)
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onClickBackButton() }
        ) {
            Icon(
                painter = painterResource(com.jyp.jyp_design.R.drawable.icon_left_arrow),
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
                .padding(end = 12.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = JypColors.Background_white200,
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = JypColors.Border_grey,
                    shape = RoundedCornerShape(6.dp)
                )
                .clickable { onClickBackButton() }
        ) {
            JypText(
                text = placeName,
                type = TextType.TITLE_3,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(start = 12.dp)
                    .weight(weight = 1f),
                color = JypColors.Text80
            )
            Spacer(modifier = Modifier.size(12.dp))
            Image(
                painter = painterResource(id = com.jyp.jyp_design.R.drawable.ic_text_delete),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 12.dp)
                    .wrapContentSize()
                    .clickable { onClickClearButton() }
            )
        }
    }
}

@Composable
private fun ComposablePlaceInfoBottomSheet(
    placeName: String,
    placeAddress: String,
    onClickInfoButton: () -> Unit,
    onClickAddPlaceButton: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(JypColors.Background_white100),
        verticalArrangement = Arrangement.Bottom
    ) {
        JypText(
            text = placeName,
            type = TextType.TITLE_1,
            modifier = Modifier.padding(horizontal = 24.dp),
            color = JypColors.Text90
        )
        JypText(
            text = placeAddress,
            type = TextType.BODY_3,
            modifier = Modifier
                .padding(top = 6.dp)
                .padding(horizontal = 24.dp),
            color = JypColors.Text40
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            Button(
                onClick = { onClickInfoButton() },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 24.dp),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = JypColors.Background_white100
                ),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Image(
                    painter = JypPainter.eyes,
                    contentDescription = null,
                    modifier = Modifier.padding(all = 0.dp)
                )
                JypText(
                    text = stringResource(id = R.string.button_see_info),
                    type = TextType.BODY_1,
                    modifier = Modifier.padding(all = 8.dp),
                    color = JypColors.Text90,
                )
            }
        }
        JypTextButton(
            text = stringResource(id = R.string.button_add_place),
            buttonType = ButtonType.THICK,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 28.dp),
            enabled = true,
            buttonColorSet = ButtonColorSetType.PINK,
            onClickEnabled = onClickAddPlaceButton,
        )
    }
}
