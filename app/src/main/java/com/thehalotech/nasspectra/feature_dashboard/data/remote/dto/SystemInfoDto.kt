package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class SystemInfoDto(
    val hostname: String,
    val physmem: Long,
    val cores: Int,
    val loadavg: List<Double>,
    val uptime_seconds: Double
)
