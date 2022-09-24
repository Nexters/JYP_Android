package com.jyp.core_network.jyp.response

data class CreateUserResponse(
        val id: String,
        val name: String,
        val profileImagePath: String,
        val personality: String,
)
