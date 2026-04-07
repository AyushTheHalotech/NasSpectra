package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class DiskDataDto(
    val disk: String,
    val status: String,
    val stats: DiskStatsDto,
    val device: String
)
