package com.jyp.feature_sign_in.util

import android.app.Activity
import android.content.Context
import android.content.Intent
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

fun Activity.setIntentTo(activity: Activity, extras: Map<String, String>? = null) {
    val intent = Intent(this, activity::class.java)
    extras?.forEach { it ->
        intent.putExtra(it.key, it.value)
    }
    startActivity(intent)
}