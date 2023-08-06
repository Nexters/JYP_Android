package com.jyp.core_network.util

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun setToken(token: String) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(TOKEN, null) ?: ""
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN).apply()
    }


    companion object {
        const val TOKEN = "TOKEN"
    }
}
