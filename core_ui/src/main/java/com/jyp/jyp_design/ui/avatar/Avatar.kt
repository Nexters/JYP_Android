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
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    avatarType: AvatarType,
    showBorder: Boolean = true,
    borderColor: Color = JypColors.Background_white100,
) {
    Box(
        modifier = if (showBorder) {
            Modifier.border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(avatarType.radius.dp),
            )
        } else {
            Modifier
        }
    ) {
        GlideImage(
            modifier = modifier
                .size(
                    width = avatarType.size.dp,
                    height = avatarType.size.dp
                )
                .clip(RoundedCornerShape(avatarType.radius.dp))
                .padding(2.dp),
            imageModel = profileImageUrl,
            previewPlaceholder = R.drawable.image_another_journey,
        )
    }
}

@Composable
internal fun MoreAvatar(
    overflowCount: Int,
    avatarType: AvatarType,
    showBorder: Boolean,
    borderColor: Color,
) {
    Box(
        modifier = Modifier
            .size(
                width = avatarType.size.dp,
                height = avatarType.size.dp
            )
            .clip(RoundedCornerShape(avatarType.radius.dp))
            .background(color = JypColors.Background_grey300)
            .let { modifier ->
                if (showBorder) {
                    modifier.border(
                        width = 2.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(avatarType.radius.dp),
                    )
                } else {
                    modifier
                }
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.padding(start = 9.dp),
            text = "$overflowCount+",
            color = JypColors.Text_white,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun AvatarList(
    modifier: Modifier = Modifier,
    profileImageUrls: List<String>,
    avatarType: AvatarType,
    showBorder: Boolean = true,
    borderColor: Color = JypColors.Background_white100,
    limitListCount: Int = Int.MAX_VALUE,
    tailContent: @Composable () -> Unit = {},
) {
    val overflowCount: Int = profileImageUrls.size - limitListCount

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(
                when (limitListCount == Int.MAX_VALUE) {
                    true -> (-(avatarType.size / 2)).dp
                    false -> (-18).dp
                }
            ),
        ) {
            if (overflowCount > 0) {
                MoreAvatar(
                    overflowCount = overflowCount + 1,
                    avatarType = avatarType,
                    showBorder = showBorder,
                    borderColor = borderColor,
                )

                profileImageUrls.subList(0, 3).forEach { url ->
                    Avatar(
                        profileImageUrl = url,
                        avatarType = avatarType,
                        showBorder = showBorder,
                        borderColor = borderColor,
                    )
                }
            } else {
                profileImageUrls.forEach { url ->
                    Avatar(
                        profileImageUrl = url,
                        avatarType = avatarType,
                        showBorder = showBorder,
                        borderColor = borderColor,
                    )
                }

                tailContent.invoke()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun AvatarPreview() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(JypColors.Background_grey300),
        contentAlignment = Alignment.Center,
    ) {
        Avatar(
            profileImageUrl = "https://file.mk.co.kr/meet/neds/2021/04/image_readtop_2021_330747_16177500644599916.jpg",
            avatarType = AvatarType.SMALL
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun AvatarListPreview() {
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
            avatarType = AvatarType.SMALL,
            showBorder = true,
            borderColor = JypColors.Background_white100,
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun AvatarListMorePreview() {
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
            avatarType = AvatarType.SMALL,
            showBorder = true,
            borderColor = JypColors.Background_white100,
            limitListCount = 4
        )
    }
}
