package com.jyp.jyp_design.ui.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun DecoratedTag(
        modifier: Modifier = Modifier,
        tagType: TagType,
        tagState: TagState,
        content: String,
        tagCount: Int,
) {
    Row(
            modifier = modifier
                    .clip(
                            RoundedCornerShape(
                                    topStart = 20.dp,
                                    bottomStart = 20.dp,
                                    topEnd = 8.dp,
                                    bottomEnd = 8.dp,
                            )
                    )
                    .background(tagType.getBackgroundColor(tagState))
                    .padding(vertical = 4.dp)
    ) {
        Spacer(modifier = Modifier.size(4.dp))
        DecoratedTagIcon(
                tagType = tagType,
                tagState = tagState,
        )
        Spacer(modifier = Modifier.size(6.dp))
        JypText(
                text = content,
                type = TextType.TAG_1,
                color = tagType.getTextColor(tagState),
        )
        if (tagCount > 0) {
            Spacer(modifier = Modifier.size(6.dp))
            JypText(
                    text = tagCount.toString(),
                    type = TextType.TAG_1,
                    color = tagType.getTextColor(tagState),
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
private fun DecoratedTagIcon(
        tagType: TagType,
        tagState: TagState,
) {
    Box(
            modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(tagType.getIconBackgroundColor(tagState)),
            contentAlignment = Alignment.Center,
    ) {
        Image(
                painter = painterResource(id = tagType.iconRes),
                colorFilter = ColorFilter.tint(tagType.getIconColor(tagState)),
                contentDescription = null,
        )
    }
}

@Composable
@Preview
internal fun DecoratedTagSosoPreview() {
    DecoratedTag(
            tagType = TagType.Soso(),
            tagState = TagState.DEFAULT,
            content = "상관없어",
            tagCount = 3,
    )
}

@Composable
@Preview
internal fun DecoratedTagLikePreview() {
    DecoratedTag(
            tagType = TagType.Like(),
            tagState = TagState.DEFAULT,
            content = "좋아용",
            tagCount = 2,
    )
}

@Composable
@Preview
internal fun DecoratedTagDisLikePreview() {
    DecoratedTag(
            tagType = TagType.Dislike(),
            tagState = TagState.DEFAULT,
            content = "좋지않아",
            tagCount = 0,
    )
}
