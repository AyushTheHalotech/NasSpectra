package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class DisksDto(
    val name: String,
    val model: String,
    val type: String,
    val rotationrate: Int?,
    val devname: String,
    val imported_zpool: String,
    val size: Long
)
