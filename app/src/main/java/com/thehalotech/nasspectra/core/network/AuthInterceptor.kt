package com.thehalotech.nasspectra.core.network

import com.thehalotech.nasspectra.feature_dashboard.domain.core.SessionManager
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (_, apiKey) = sessionManager.credentials.value

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        return chain.proceed(request)
    }
}