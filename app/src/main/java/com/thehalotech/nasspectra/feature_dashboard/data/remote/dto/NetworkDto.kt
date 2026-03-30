package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class NetworkDto(
    val id: String,
    val name: String,
    val type: String,
    val state: NetworkStateDto
)
