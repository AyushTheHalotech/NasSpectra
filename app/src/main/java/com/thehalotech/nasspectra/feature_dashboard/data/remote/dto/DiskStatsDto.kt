package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class DiskStatsDto(
    val read_errors: Int,
    val write_errors: Int,
    val checksum_errors: Int
)
