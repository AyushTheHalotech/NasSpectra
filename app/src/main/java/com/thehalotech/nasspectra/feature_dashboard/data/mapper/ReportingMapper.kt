package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.ReportingResponseDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.SystemInfoDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.CpuTempsState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.CpuUsageInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.MemoryStatInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.ReportingInfo

fun List<ReportingResponseDto>.toDomain(totalMem: Long, systemInfo: SystemInfoDto): ReportingInfo {

    var cpuUsage: CpuUsageInfo? = null
    var cpuTemp: CpuTempsState? = null
    var memory: MemoryStatInfo? = null


    forEach { dto ->
        when (dto.name) {
            "cpu" -> cpuUsage = dto.toCpuInfo()
            "cputemp" -> cpuTemp = dto.toCpuTempInfo()
            "memory" -> memory = dto.toMemoryInfo(totalMem)
        }
    }

    return ReportingInfo(
        cpuUsage = cpuUsage ?: CpuUsageInfo(0.0),
        cpuTemp = cpuTemp?: CpuTempsState(0.0),
        memUsage = memory ?: MemoryStatInfo(0.0, 0.0, 0.0),
        systemInfo = systemInfo.toDomain()
    )
}

fun ReportingResponseDto.toCpuInfo(): CpuUsageInfo {
    return CpuUsageInfo((this.data.last()[1]).toDouble())
}

fun ReportingResponseDto.toCpuTempInfo(): CpuTempsState {
    return CpuTempsState((this.data.last()[1]).toDouble())
}

fun ReportingResponseDto.toMemoryInfo(totMem: Long): MemoryStatInfo {
    val availMem = this.data.last()[1] / (1024.0 * 1024 * 1024)
    val totalMem = totMem / (1024.0 * 1024 * 1024)
    val usedMem = totalMem - availMem
    return MemoryStatInfo(
        totalMem = totalMem,
        usedMem = usedMem,
        availMem = availMem)
}