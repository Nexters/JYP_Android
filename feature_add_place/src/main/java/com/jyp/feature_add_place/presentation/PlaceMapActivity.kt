package com.jyp.feature_add_place.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jyp.feature_add_place.data.model.SearchPlaceResultModel
import com.jyp.jyp_design.R
import com.jyp.feature_add_place.databinding.ActivityPlaceMapBinding
import com.jyp.feature_add_place.presentation.SearchPlaceActivity.Companion.SEARCH_PLACE_RESULT
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.button.ButtonColorSetType
import com.jyp.jyp_design.ui.button.ButtonType
import com.jyp.jyp_design.ui.button.JypTextButton
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.text_input.JypTextInput
import com.jyp.jyp_design.ui.text_input.TextInputType
import com.jyp.jyp_design.ui.typography.type.TextType
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class PlaceMapActivity : ComponentActivity() {

    private val viewBinding: ActivityPlaceMapBinding by lazy {
        ActivityPlaceMapBinding.inflate(layoutInflater)
    }
    private lateinit var mapView: MapView
    private val searchPlaceResultModel: SearchPlaceResultModel? by lazy {
        intent.getParcelableExtra(SEARCH_PLACE_RESULT)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initComposeView()
        initKakaoMapView()
    }

    private fun initComposeView() {
        viewBinding.composeViewAppBar.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposableAppBar(
                    placeName = searchPlaceResultModel?.name ?: "",
                    onClickBackButton = { this@PlaceMapActivity.finish() },
                    onClickClearButton = {
                        startActivity(
                            Intent(this@PlaceMapActivity, SearchPlaceActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                        )
                    }
                )
            }
        }
        viewBinding.composeViewMapMarker.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                searchPlaceResultModel?.let {
                    ComposablePlaceMarker(
                        placeName = it.name,
                        placeCategory = getString(it.categoryEnum.journeyPikiCategoryNameRes)
                    )
                }
            }
        }
        viewBinding.composeViewPlaceInfo.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                searchPlaceResultModel?.let {
                    ComposablePlaceInfoBottomSheet(
                        placeName = it.name,
                        placeAddress = it.address,
                        onClickInfoButton = {
                            startActivity(
                                Intent(context, PlaceInfoActivity::class.java).apply {
                                    putExtra(SEARCH_PLACE_RESULT, searchPlaceResultModel)
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    private fun initKakaoMapView() {
        mapView = MapView(this).apply {
            viewBinding.frameLayoutMapViewContainer.addView(this)
            setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    searchPlaceResultModel?.x ?: 0.0,
                    searchPlaceResultModel?.y ?: 0.0
                ),
                true)
        }
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
            .clip(RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(1.dp, JypColors.Border_grey),
                shape = RoundedCornerShape(6.dp)
            )
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
                painter = painterResource(id = R.drawable.ic_text_delete),
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
    onClickInfoButton: () -> Unit
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
            modifier = Modifier
                .padding(top = 8.dp)
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
                    painter = painterResource(id = com.jyp.feature_add_place.R.drawable.icon_eyes),
                    contentDescription = null,
                    modifier = Modifier.padding(all = 0.dp)
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
            painter = painterResource(id = com.jyp.feature_add_place.R.drawable.image_map_marker_tail),
            contentDescription = null,
            colorFilter = ColorFilter.tint(JypColors.Background_white100),
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
            painter = painterResource(id = com.jyp.feature_add_place.R.drawable.icon_map_marker),
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