package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils

import com.thehalotech.nasspectra.feature_dashboard.domain.util.DomainError
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState

fun <T> SectionState<T>.toLoading() = copy(
    isLoading = data == null,
    isRefreshing = data != null
)

fun <T> SectionState<T>.toError(error: DomainError) = copy(
    isLoading = false,
    isRefreshing = false,
    error = error
)