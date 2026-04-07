package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils

import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo

fun findDiskMeanTemp(diskList: List<DiskInfo>): Double {
    val temps = diskList.mapNotNull { it.temperature }

    return if (temps.isNotEmpty()) {
        temps.average()
    } else {
        0.0 // or 0.0 depending on your use case
    }
}