package com.jyp.feature_my_page.data

import com.jyp.core_network.jyp.model.response.WithdrawalResponse
import com.jyp.feature_my_page.domain.WithdrawalRepository
import javax.inject.Inject


class WithdrawalRepositoryImpl @Inject constructor(
    private val withdrawalDataSource: WithdrawalDataSource
) : WithdrawalRepository {

    override suspend fun withdrawAccount(
        userId: String
    ): WithdrawalResponse {
        return withdrawalDataSource.withdrawAccount(userId)
    }
}