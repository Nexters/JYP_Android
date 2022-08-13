package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.R
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBar
import com.jyp.jyp_design.ui.gnb.GlobalNavigationBarColor
import com.jyp.jyp_design.ui.text.Text
import com.jyp.jyp_design.ui.typography.type.TextType

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlannerScreen() {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)

    BackdropScaffold(
            scaffoldState = scaffoldState,
            appBar = {
                GlobalNavigationBar(
                        color = GlobalNavigationBarColor.BLACK,
                        title = "강릉 여행기",
                        activeBack = true,
                )
            },
            backLayerContent = {
                PlannerBackLayer()
            },
            frontLayerContent = {
                PlannerContent()
            },
            backLayerBackgroundColor = JypColors.Background_grey300
    )
}

@Composable
private fun PlannerBackLayer() {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
                text = "7월 18일 - 7월 20일",
                type = TextType.BODY_1,
                color = JypColors.Text_white,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
                modifier = Modifier
                        .height(40.dp),
                onClick = {  },
                colors = ButtonDefaults.buttonColors(backgroundColor = JypColors.Pink),
                contentPadding = PaddingValues(0.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.icon_smile_plus),
                    contentDescription = null,
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                    text = "일행 초대하기",
                    type = TextType.BODY_3,
                    color = JypColors.Text_white,
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun PlannerContent() {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .clip(
                            RoundedCornerShape(
                                    topStart = 24.dp,
                                    topEnd = 24.dp,
                            )
                    )
                    .background(JypColors.Background_white100),
    ) {

    }
}

@Composable
@Preview(showBackground = true)
internal fun PlannerScreenPreview() {
    PlannerScreen()
}
