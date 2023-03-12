package com.jyp.feature_my_page.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.WithdrawalResponse
import javax.inject.Inject


class WithdrawalDataSource @Inject constructor(
    private val jypApi: JypApi
) {
    suspend fun withdrawAccount(
        userId: String
    ): WithdrawalResponse {
        return jypApi.withdrawAccount(userId)
    }
}
