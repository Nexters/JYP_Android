package com.jyp.feature_planner.presentation.planner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jyp.feature_planner.domain.Person
import com.jyp.feature_planner.domain.PlannerTag
import com.jyp.jyp_design.resource.JypColors
import com.jyp.jyp_design.ui.avatar.Avatar
import com.jyp.jyp_design.ui.avatar.AvatarType
import com.jyp.jyp_design.ui.tag.DecoratedTag
import com.jyp.jyp_design.ui.tag.TagType
import com.jyp.jyp_design.ui.text.JypText
import com.jyp.jyp_design.ui.typography.type.TextType

@Composable
internal fun TagSelectedBottomSheetScreen(
    tag: PlannerTag,
    onClickTagInfoCloseButton: () -> Unit
) {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                            top = 27.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 40.dp,
                    ),
    ) {
        Header(
            tag.type,
            onClickTagInfoCloseButton
        )
        Spacer(modifier = Modifier.size(24.dp))
        DecoratedTag(
                tagType = tag.type,
                tagState = tag.state,
                content = tag.content,
                tagCount = tag.selectPeople.size,
        )
        Spacer(modifier = Modifier.size(12.dp))
        People(people = tag.selectPeople)
    }
}

@Composable
private fun Header(
    tagType: TagType,
    onClickTagInfoCloseButton: () -> Unit
) {
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        JypText(
                text = when (tagType) {
                    TagType.Soso -> "상관 없어요 태그"
                    TagType.Like -> "좋아요 태그"
                    TagType.Dislike -> "싫어요 태그"
                },
                type = TextType.TITLE_2,
                color = JypColors.Text80,
        )
        JypText(
                modifier = Modifier.clickable {
                    onClickTagInfoCloseButton()
                },
                text = "확인",
                type = TextType.BODY_2,
                color = JypColors.Text40,
        )
    }
}

@Composable
private fun People(people: List<Person>) {
    val rememberScrollState = rememberScrollState()
    Row(
            modifier = Modifier.horizontalScroll(rememberScrollState),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        people.forEach { person ->
            Person(person = person)
        }
    }
}

@Composable
private fun Person(person: Person) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(
                profileImageUrl = person.profileUrl,
                avatarType = AvatarType.SMALL
        )
        Spacer(modifier = Modifier.size(8.dp))
        JypText(
                text = person.name,
                type = TextType.BODY_3,
                color = JypColors.Text75,
        )
    }
}
