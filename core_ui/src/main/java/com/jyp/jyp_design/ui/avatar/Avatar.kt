package com.jyp.jyp_design.ui.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Avatar(
        profileImageUrl: String,
        width: Dp,
        height: Dp,
        showBorder: Boolean,
        borderColor: Color,
) {
    GlideImage(
            modifier = Modifier
                    .size(width = width, height = height)
                    .clip(RoundedCornerShape(12.dp))
                    .let { modifier ->
                        if (showBorder) {
                            modifier.border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(12.dp),
                            )
                        } else {
                            modifier
                        }
                    },
            imageModel = profileImageUrl,
            previewPlaceholder = R.drawable.ic_search,
    )
}

@Composable
fun MoreAvatar(
        overflowCount: Int,
        width: Dp,
        height: Dp,
        showBorder: Boolean,
        borderColor: Color,
) {
    Box(
            modifier = Modifier
                    .size(width = width, height = height)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = JypColors.Background_grey300)
                    .let { modifier ->
                        if (showBorder) {
                            modifier.border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(12.dp),
                            )
                        } else {
                            modifier
                        }
                    },
            contentAlignment = Alignment.CenterStart,
    ) {
        Text(
                modifier = Modifier
                        .padding(start = 9.dp),
                text = "$overflowCount+",
                color = JypColors.Text_white,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun AvatarList(
        profileImageUrls: List<String>,
        width: Dp,
        height: Dp,
        showBorder: Boolean,
        borderColor: Color,
) {
    val overflowCount: Int = profileImageUrls.size - 4

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
                horizontalArrangement = Arrangement.spacedBy((-18).dp),
        ) {
            if (overflowCount > 0) {
                MoreAvatar(
                        overflowCount = overflowCount + 1,
                        width = width,
                        height = height,
                        showBorder = showBorder,
                        borderColor = borderColor,
                )

                profileImageUrls.subList(0, 3).forEach { url ->
                    Avatar(
                            profileImageUrl = url,
                            width = width,
                            height = height,
                            showBorder = showBorder,
                            borderColor = borderColor,
                    )
                }
            } else {
                profileImageUrls.forEach { url ->
                    Avatar(
                            profileImageUrl = url,
                            width = width,
                            height = height,
                            showBorder = showBorder,
                            borderColor = borderColor,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AvatarPreview() {
    Box(
            modifier = Modifier
                    .size(100.dp)
                    .background(JypColors.Background_grey300),
            contentAlignment = Alignment.Center,
    ) {
        Avatar(
                profileImageUrl = "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                width = 44.dp,
                height = 44.dp,
                showBorder = true,
                borderColor = JypColors.Background_white100,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AvatarListPreview() {
    Box(
            modifier = Modifier
                    .size(width = 300.dp, height = 100.dp)
                    .background(JypColors.Background_grey300),
            contentAlignment = Alignment.CenterStart,
    ) {
        AvatarList(
                profileImageUrls = listOf(
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                ),
                width = 44.dp,
                height = 44.dp,
                showBorder = true,
                borderColor = JypColors.Background_white100,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AvatarListMorePreview() {
    Box(
            modifier = Modifier
                    .size(width = 300.dp, height = 100.dp)
                    .background(JypColors.Background_grey300),
            contentAlignment = Alignment.CenterStart,
    ) {
        AvatarList(
                profileImageUrls = listOf(
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                        "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
                ),
                width = 44.dp,
                height = 44.dp,
                showBorder = true,
                borderColor = JypColors.Background_white100,
        )
    }
}
