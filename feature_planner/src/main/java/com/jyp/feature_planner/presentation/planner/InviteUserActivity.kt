package com.jyp.feature_planner.presentation.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InviteUserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickBackButton = { this.finish() },
                onClickCopyInvitationLinkButton = { }
            )
        }
    }
}

@Composable
private fun Screen(
    onClickBackButton: () -> Unit,
    onClickCopyInvitationLinkButton: () -> Unit
) {
    InviteUserScreen(
        onClickBackButton,
        onClickCopyInvitationLinkButton
    )
}