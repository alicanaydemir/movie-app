package com.aydemir.movieapp.util.service

import android.content.Context
import com.aydemir.movieapp.BuildConfig
import com.aydemir.movieapp.util.helper.SharedHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val sharedHelper: SharedHelper, val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val modifiedRequest: Request =
            request.newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.API_KEY)
                .header("Content-Type", "application/json")
                .build()

        return chain.proceed(modifiedRequest)
    }
}