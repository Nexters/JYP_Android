package com.jyp.core_network.di

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JypSessionManager @Inject constructor() {
    var bearerToken: String? = null
}