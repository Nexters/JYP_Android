package com.jyp.feature_planner.presentation.planner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InviteUserActivity : ComponentActivity() {

    private val clipboardManager get() =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                onClickBackButton = { this.finish() },
                onClickCopyInvitationCodeButton = {
                    intent?.getStringExtra(PlannerActivity.EXTRA_PLANNER_ID)?.let {
                        clipboardManager.setPrimaryClip(
                            ClipData.newPlainText(it, it)
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun Screen(
    onClickBackButton: () -> Unit,
    onClickCopyInvitationCodeButton: () -> Unit
) {
    InviteUserScreen(
        onClickBackButton,
        onClickCopyInvitationCodeButton
    )
}