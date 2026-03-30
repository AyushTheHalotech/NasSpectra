package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class SystemStats(
    val hostname: String,
    val cpuUsagePercent: Double,
    val totalMemoryGb: Double,
    val uptimeHours: Int,
    val cpuCores: Int
)
