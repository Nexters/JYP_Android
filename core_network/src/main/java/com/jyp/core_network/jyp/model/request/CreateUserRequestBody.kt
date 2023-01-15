package com.jyp.core_network.jyp.model.request


data class CreateUserRequestBody(
        val name: String,
        val profileImagePath: String,
        val personalityId: String,
)
