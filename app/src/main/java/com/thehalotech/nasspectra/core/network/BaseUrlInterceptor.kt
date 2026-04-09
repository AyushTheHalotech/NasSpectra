package com.thehalotech.nasspectra.core.network

import com.thehalotech.nasspectra.feature_dashboard.domain.core.SessionManager
import jakarta.inject.Inject
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (baseUrl, _) = sessionManager.credentials.value

        if (baseUrl.isBlank()) return chain.proceed(chain.request())

        val newBase = baseUrl.toHttpUrl()
        val original = chain.request()

        val newUrl = original.url.newBuilder()
            .scheme(newBase.scheme)
            .host(newBase.host)
            .port(newBase.port)
            .encodedPath(
                newBase.encodedPath.trimEnd('/') + "/" +
                        original.url.encodedPath.trimStart('/')
            )
            .build()

        return chain.proceed(original.newBuilder().url(newUrl).build())
    }
}