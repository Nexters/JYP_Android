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
import com.jyp.jyp_design.ui.text.Text
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
fun DecoratedTag(
        tagType: TagType,
        content: String,
) {
    Row(
            modifier = Modifier
                    .clip(
                            RoundedCornerShape(
                                    topStart = 20.dp,
                                    bottomStart = 20.dp,
                                    topEnd = 8.dp,
                                    bottomEnd = 8.dp,
                            )
                    )
                    .background(tagType.backgroundColor)
                    .padding(vertical = 4.dp)
    ) {
        Spacer(modifier = Modifier.size(4.dp))
        DecoratedTagIcon(
                tagType = tagType
        )
        Spacer(modifier = Modifier.size(9.dp))
        Text(
                text = content,
                type = TextType.TAG_1,
                color = tagType.textColor,
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
private fun DecoratedTagIcon(tagType: TagType) {
    Box(
            modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(tagType.iconBackgroundColor),
            contentAlignment = Alignment.Center,
    ) {
        Image(
                painter = painterResource(id = tagType.iconRes),
                colorFilter = ColorFilter.tint(tagType.iconColor),
                contentDescription = null,
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun DecoratedTagSosoPreview() {
    DecoratedTag(
            tagType = TagType.Soso(),
            "상관없어"
    )
}

@Composable
@Preview(showBackground = true)
internal fun DecoratedTagLikePreview() {
    DecoratedTag(
            tagType = TagType.Like(),
            "좋아용"
    )
}

@Composable
@Preview(showBackground = true)
internal fun DecoratedTagDisLikePreview() {
    DecoratedTag(
            tagType = TagType.Dislike(),
            "좋지않아"
    )
}
