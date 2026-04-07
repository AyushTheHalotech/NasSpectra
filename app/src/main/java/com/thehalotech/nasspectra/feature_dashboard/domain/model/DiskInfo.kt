package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class DiskInfo(
    val name: String,
    val model: String?,
    val type: String?,
    val temperature: Double?,
    val rotationrate: Int,
    val devname: String,
    val imported_zpool: String,
    val size: Double
)
