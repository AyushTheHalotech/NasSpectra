package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.SystemInfoDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats

fun SystemInfoDto.toDomain(): SystemStats {
    val cpuUsage = (loadavg[0] / cores) * 100
    val memoryGb = physmem / (1024.0 * 1024 * 1024)
    val uptimeHours = (uptime_seconds / 3600).toInt()

    return SystemStats(
        hostname = hostname,
        cpuUsagePercent = cpuUsage,
        totalMemoryGb = memoryGb,
        uptimeHours = uptimeHours,
        cpuCores = cores
    )
}