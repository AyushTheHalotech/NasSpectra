package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class DiskScanDto(
    val function: String,
    val state: String,
    val errors: Int,
)
