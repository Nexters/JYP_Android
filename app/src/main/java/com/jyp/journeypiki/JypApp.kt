package com.jyp.journeypiki

import android.app.Application
import com.jyp.feature_sign_in.util.MySharedPreferences
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class JypApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(applicationContext)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
