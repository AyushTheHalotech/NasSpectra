package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.SystemInfoDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats

fun SystemInfoDto.toDomain(): SystemStats {
    val memoryGb = physmem / (1024.0 * 1024 * 1024)
    val totalSeconds = uptime_seconds.toLong()

    val days = totalSeconds / (24 * 60 * 60)
    val hours = (totalSeconds % (24 * 60 * 60)) / (60 * 60)
    val minutes = (totalSeconds % (60 * 60)) / 60
    val seconds = totalSeconds % 60

    return SystemStats(
        hostname = hostname,
        uptimeDays = days.toInt(),
        uptimeHours = hours.toInt(),
        uptimeMinutes = minutes.toInt(),
        uptimeSeconds = seconds.toInt(),
        totalCores = cores,
        physicalCores = physical_cores,
        totalInstalledMemory = memoryGb,
        systemName = system_product_version,
        systemSerial = system_serial,
        timeZone = timezone,
        systemManufacturer = system_manufacturer,
        systemVersion = version,
        cpuName = model
    )
}