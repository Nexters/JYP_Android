package com.jyp.core_util.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle


fun <T> Context.setIntentTo(
    it: Class<T>,
    flags: Int? = null,
    extras: Bundle.() -> Unit = {}
) {
    Intent(this, it).apply {
        putExtras(Bundle().apply(extras))
        flags?.let { this.flags = it }
        startActivity(this)
    }
}