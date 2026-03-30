package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class NetworkState(
    val id: String,
    val name: String,
    val linkState: String,
    val iNetAddress: String,
    val linkSpeed: String
)
