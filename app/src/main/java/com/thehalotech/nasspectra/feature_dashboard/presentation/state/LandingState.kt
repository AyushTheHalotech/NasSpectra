package com.thehalotech.nasspectra.feature_dashboard.presentation.state

data class LandingState(
    val ip: String = "",
    val apiKey: String = "",
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val error: String? = null
)
