package com.jyp.core.header


class UserInfoHeaderProvider {

    fun getUserInfoHeaders(jwtToken: String): HashMap<String,String> {
        return hashMapOf(
            AUTHORIZATION to getBearer(jwtToken),
            CONTENT_TYPE to APPLICATION_JSON
        )
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val CONTENT_TYPE = "Content-Type"

        private const val APPLICATION_JSON = "application/json"
        private fun getBearer(jwtToken: String) = "Bearer $jwtToken"
    }
}
