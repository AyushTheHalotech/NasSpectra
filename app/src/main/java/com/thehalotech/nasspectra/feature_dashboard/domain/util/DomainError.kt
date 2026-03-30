package com.thehalotech.nasspectra.feature_dashboard.domain.util

sealed class DomainError {
    object Network : DomainError()
    object Unauthorized : DomainError()
    object NotFound : DomainError()
    object Unknown : DomainError()
}