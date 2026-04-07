package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.DisksDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import kotlin.collections.map

fun DisksDto.toDomain(): DiskInfo {
    return DiskInfo(
        name = name,
        model = model,
        type = type,
        temperature = null,
        rotationrate = rotationrate?: 0,
        devname = devname,
        imported_zpool = imported_zpool,
        size = bytesToGB(size)
    )
}

fun List<DisksDto>.toDomain(): List<DiskInfo> {
    return map { it.toDomain() }
}
private fun bytesToGB(bytes: Long): Double {
    return bytes / (1024 * 1024 * 1024.0)
}
