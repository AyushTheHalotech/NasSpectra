package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class ReportingInfo(
    val cpuUsage: CpuUsageInfo,
    val cpuTemp: CpuTempsState,
    val memUsage: MemoryStatInfo,
    val systemInfo: SystemStats
)
