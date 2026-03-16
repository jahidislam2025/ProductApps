package com.qsoft.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class PublicInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder()
                .build()
        return chain.proceed(newRequest)
    }
}