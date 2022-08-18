package com.jyp.feature_my_journey.presentation.my_journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyp.feature_my_journey.domain.Journey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyJourneyViewModel @Inject constructor(
        // use case..
) : ViewModel() {
    private val _plannedJourneys = MutableStateFlow(listOf<Journey>())
    val plannedJourneys: StateFlow<List<Journey>>
        get() = _plannedJourneys

    private val _pastJourneys = MutableStateFlow(listOf<Journey>())
    val pastJourneys: StateFlow<List<Journey>>
        get() = _pastJourneys

    fun fetchJourneyList() {
        viewModelScope.launch {
            _plannedJourneys.value = listOf(
                    Journey(
                            dDay = "D-3",
                            title = "강릉여행기",
                            theme = 0,
                            startDay = "8월 23일",
                            endDay = "8월 25일",
                            profileUrls = listOf(
                                    "https://img.marieclairekorea.com/2022/02/mck_620b83ff0751b.jpg",
                                    "https://newsimg.hankookilbo.com/cms/articlerelease/2021/05/17/b41ab909-e0e2-40e8-a36a-4bae809a9024.jpg",
                            )
                    ),
                    Journey(
                            dDay = "D-8",
                            title = "즐거운여행기",
                            theme = 1,
                            startDay = "8월 28일",
                            endDay = "8월 30일",
                            profileUrls = listOf(
                                    "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/U4IIQNIIYFLA3JC7EYMNJMOCIA.JPG",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://dimg.donga.com/wps/NEWS/IMAGE/2021/05/17/106977090.2.jpg",
                            )
                    ),
                    Journey(
                            dDay = "D-31",
                            title = "행복한여행기",
                            theme = 2,
                            startDay = "9월 20일",
                            endDay = "9월 25일",
                            profileUrls = listOf(
                                    "https://img.marieclairekorea.com/2022/02/mck_620b83ff0751b.jpg",
                                    "https://newsimg.hankookilbo.com/cms/articlerelease/2021/05/17/b41ab909-e0e2-40e8-a36a-4bae809a9024.jpg",
                                    "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/U4IIQNIIYFLA3JC7EYMNJMOCIA.JPG",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://dimg.donga.com/wps/NEWS/IMAGE/2021/05/17/106977090.2.jpg",
                                    "https://dimg.donga.com/wps/NEWS/IMAGE/2021/05/17/106977090.2.jpg",
                                    "https://dimg.donga.com/wps/NEWS/IMAGE/2021/05/17/106977090.2.jpg",
                            )
                    ),
                    Journey(
                            dDay = "D-40",
                            title = "점잖은여행기",
                            theme = 3,
                            startDay = "9월 29일",
                            endDay = "10월 25일",
                            profileUrls = listOf(
                                    "https://dimg.donga.com/wps/NEWS/IMAGE/2021/05/17/106977090.2.jpg",
                                    "https://img.marieclairekorea.com/2022/02/mck_620b83ff0751b.jpg",
                                    "https://newsimg.hankookilbo.com/cms/articlerelease/2021/05/17/b41ab909-e0e2-40e8-a36a-4bae809a9024.jpg",
                                    "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/U4IIQNIIYFLA3JC7EYMNJMOCIA.JPG",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                                    "https://biz.chosun.com/resizer/dsZb3djSs5sc6SFm0xr302MixnM=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/5LTZLDMDHKG2QTJO6K5DD2IKKI.jpg",
                            )
                    ),
            )
        }
    }
}
