package com.thehalotech.nasspectra.feature_dashboard.domain.util

import jakarta.inject.Inject

class ErrorMapper @Inject constructor() {

    fun map(e: Exception): DomainError {
        return when (e) {
            is java.io.IOException -> DomainError.Network
            is retrofit2.HttpException -> {
                when (e.code()) {
                    401 -> DomainError.Unauthorized
                    404 -> DomainError.NotFound
                    else -> DomainError.Unknown
                }
            }
            else -> DomainError.Unknown
        }
    }
}