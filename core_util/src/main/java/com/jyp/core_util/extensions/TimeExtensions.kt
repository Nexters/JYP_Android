package com.jyp.core_util.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.secondToDate(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(Date(this * 1000))
}
