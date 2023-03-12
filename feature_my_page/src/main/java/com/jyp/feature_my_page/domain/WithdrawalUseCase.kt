package com.jyp.feature_my_page.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.Withdrawal
import com.jyp.core_network.jyp.model.response.Journeys
import javax.inject.Inject


class WithdrawalUseCase @Inject constructor(
    private val withdrawalRepository: WithdrawalRepository
) {
    suspend operator fun invoke(
        userId: String
    ): ApiResult<Withdrawal> {
        return apiResult {
            withdrawalRepository.withdrawAccount(userId)
        }
    }
}
