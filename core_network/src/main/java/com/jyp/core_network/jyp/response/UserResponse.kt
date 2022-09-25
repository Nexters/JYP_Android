package com.jyp.core_network.jyp.response

import com.jyp.core_network.jyp.JypBaseResponse

class UserResponse : JypBaseResponse<User>()

data class User(
        val id: String,
        val name: String,
        val profileImagePath: String,
        val personality: String,
)
