package com.jyp.core_network.jyp.model.request

import com.jyp.core_network.jyp.model.enumerate.PersonalityId

data class CreateUserRequestBody(
        val authVendor: String,
        val authId: String,
        val name: String,
        val profileImagePath: String,
        val personalityId: PersonalityId,
)
