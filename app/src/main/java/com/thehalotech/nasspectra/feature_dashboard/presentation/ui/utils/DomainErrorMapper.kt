package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils

import com.thehalotech.nasspectra.feature_dashboard.domain.util.DomainError

fun DomainError.toUiMessage(): String {
    return when (this) {
        DomainError.Network -> "No internet connection"
        DomainError.Unauthorized -> "Session expired"
        DomainError.NotFound -> "Data not found"
        DomainError.Unknown -> "Something went wrong"
    }
}