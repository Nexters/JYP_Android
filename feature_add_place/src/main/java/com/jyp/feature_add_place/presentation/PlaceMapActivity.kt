package com.jyp.feature_add_place.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.jyp.jyp_design.R
import com.jyp.feature_add_place.databinding.ActivityPlaceMapBinding
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class PlaceMapActivity : Activity() {

    private var _viewBinding: ActivityPlaceMapBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val mapView = MapView(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initComposeView()
        initKakaoMapView()
    }

    private fun initViewBinding() {
        _viewBinding = ActivityPlaceMapBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    private fun initComposeView() {
        // Todo - Get place info from intent.
        val placeName = intent.getStringExtra(PLACE_NAME) ?: "아르떼 뮤지엄"
        val placeCategory = intent.getStringExtra(PLACE_CATEGORY) ?: "문화시설"
        val placeAddress = intent.getStringExtra(PLACE_ADDRESS) ?: "강원 강릉시 난설헌로 131"

        viewBinding.composeViewAppBar.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposableAppBar(
                    activity = this@PlaceMapActivity,
                    placeName = placeName
                )
            }
        }
        viewBinding.composeViewMapMarker.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposablePlaceMarker(
                    placeName = placeName,
                    placeCategory = placeCategory
                )
            }
        }
        viewBinding.composeViewPlaceInfo.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposablePlaceInfoBottomSheet(
                    placeName = placeName,
                    placeAddress = placeAddress
                )
            }
        }
    }

    private fun initKakaoMapView() {
        // Todo - Get place info from intent.
        val placeLatitude = intent.getDoubleExtra(PLACE_LATITUDE, 0.0)
        val placeLongitude = intent.getDoubleExtra(PLACE_LONGITUDE, 0.0)

        mapView.apply {
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(placeLatitude, placeLongitude), true)
        }
        viewBinding.frameLayoutMapViewContainer.addView(mapView)
    }
    
    companion object {
        private const val PLACE_NAME = "PLACE_NAME"
        private const val PLACE_CATEGORY = "PLACE_CATEGORY"
        private const val PLACE_ADDRESS = "PLACE_ADDRESS"
        private const val PLACE_LATITUDE = "PLACE_LATITUDE"
        private const val PLACE_LONGITUDE = "PLACE_LONGITUDE"
    }
}

@Composable
private fun ComposableAppBar(
    activity: Activity,
    placeName: String
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { activity.finish() }
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_left_arrow),
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
                .background(
                    color = JypColors.Background_white200,
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = JypColors.Border_grey,
                    shape = RoundedCornerShape(6.dp)
                )
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
                painter = painterResource(id = R.drawable.ic_text_delete),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 12.dp)
                    .wrapContentSize()
            )
        }
    }
}

@Composable
private fun ComposablePlaceInfoBottomSheet(
    placeName: String,
    placeAddress: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = JypColors.Background_white100,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            ),
        verticalArrangement = Arrangement.Bottom
    ) {
        JypText(
            text = placeName,
            type = TextType.TITLE_1,
            modifier = Modifier
                .padding(top = 28.dp)
                .padding(horizontal = 24.dp),
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            val context = LocalContext.current
            val intent = Intent(context, PlaceInfoActivity::class.java)
            Button(
                onClick = { ContextCompat.startActivity(context, intent, null) },
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
                Icon(
                    painter = painterResource(id = R.drawable.icon_eyes),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                JypText(
                    text = stringResource(id = com.jyp.feature_add_place.R.string.button_see_info),
                    type = TextType.BODY_1,
                    modifier = Modifier.padding(all = 8.dp),
                    color = JypColors.Text90,
                )
            }
        }
        JypTextButton(
            text = stringResource(id = com.jyp.feature_add_place.R.string.button_add_place),
            buttonType = ButtonType.THICK,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 28.dp),
            enabled = true,
            buttonColorSet = ButtonColorSetType.PINK
        )
    }
}

@Composable
internal fun ComposablePlaceMarker(
    placeName: String,
    placeCategory: String
) {
    ConstraintLayout(
        modifier = Modifier.wrapContentSize()
    ) {
        val (markerImage, infoColumn, markerTail) = createRefs()
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = JypColors.Background_white100,
                    shape = RoundedCornerShape(24.dp)
                )
                .constrainAs(infoColumn) {
                    top.linkTo(anchor = markerImage.top, margin = 37.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            JypText(
                text = placeName,
                type = TextType.TITLE_6,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                color = JypColors.Text80,
                textAlign = TextAlign.Center
            )
            JypText(
                text = placeCategory,
                type = TextType.BODY_4,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                color = JypColors.Text80,
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(id = R.drawable.image_map_marker_tail),
            contentDescription = null,
            modifier = Modifier
                .width(16.dp)
                .wrapContentHeight()
                .constrainAs(markerTail) {
                    top.linkTo(infoColumn.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.icon_map_marker),
            contentDescription = null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .constrainAs(ref = markerImage) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                }
        )
    }
}