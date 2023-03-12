package com.jyp.feature_my_page.domain

import com.jyp.core_network.jyp.model.response.WithdrawalResponse


interface WithdrawalRepository {
    suspend fun withdrawAccount(
        userId: String
    ): WithdrawalResponse
}
