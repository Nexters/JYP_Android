package com.jyp.core_network.jyp

abstract class JypBaseResponse<T : Any> {
    lateinit var code: String
    lateinit var message: String
    lateinit var data: T
}
