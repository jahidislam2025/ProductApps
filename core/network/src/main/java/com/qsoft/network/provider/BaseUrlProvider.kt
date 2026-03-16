package com.qsoft.network.provider

import com.qsoft.network.di.RestConfig

class BaseUrlProvider() {
    fun getBaseUrl(): String {
        return RestConfig.BASE_URL
    }
}