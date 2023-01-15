package com.jyp.feature_sign_in.util

import android.content.Context
import android.content.SharedPreferences


object MySharedPreferences {

    private lateinit var sharedPreferences: SharedPreferences

    private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
    private const val USER_ID = "USER_ID"
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"


    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    val getUserId: String get() =
        sharedPreferences.getString(USER_ID, "") ?: ""
    fun setUserId(value: String) =
        sharedPreferences.edit().putString(USER_ID, value).commit()

    val getAccessToken: String get() =
        sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
    fun setAccessToken(value: String) =
        sharedPreferences.edit().putString(ACCESS_TOKEN, value).commit()


    fun clearSharedPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}