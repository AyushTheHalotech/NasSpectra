package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class SystemInfoDto(
    val version: String,
    val hostname: String,
    val physmem: Long,
    val model: String,
    val cores: Int,
    val physical_cores: Int,
    val uptime_seconds: Double,
    val system_serial: String,
    val system_product_version: String,
    val timezone: String,
    val system_manufacturer: String
)