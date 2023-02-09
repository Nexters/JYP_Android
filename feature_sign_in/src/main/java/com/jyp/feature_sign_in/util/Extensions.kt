package com.jyp.feature_sign_in.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast


private var toast: Toast? = null
fun Context.showToast(message: Any) {
    toast?.cancel()
    toast = when (message) {
        is Int -> Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        is String -> Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        else -> null
    }
    toast?.show()
}

fun <T> Context.setIntentTo(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    Intent(this, it).apply {
        putExtras(Bundle().apply(extras))
        startActivity(this)
    }
}