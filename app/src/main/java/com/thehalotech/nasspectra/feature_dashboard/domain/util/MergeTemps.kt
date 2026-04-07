package com.thehalotech.nasspectra.feature_dashboard.domain.util

import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo

fun List<DiskInfo>.mergeTemperatures(
    temperatureMap: Map<String, Double?>
): List<DiskInfo> {
    return map { disk ->
        disk.copy(
            temperature = temperatureMap[disk.name]
        )
    }
}