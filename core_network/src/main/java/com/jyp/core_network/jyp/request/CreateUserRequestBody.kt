package com.jyp.core_network.jyp.request

import com.jyp.core_network.jyp.enumerate.PersonalityId

data class CreateUserRequestBody(
        val authVendor: String,
        val authId: String,
        val name: String,
        val profileImagePath: String,
        val personalityId: PersonalityId,
)
