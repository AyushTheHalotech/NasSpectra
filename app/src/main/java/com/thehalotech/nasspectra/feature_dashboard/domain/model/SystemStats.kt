package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class SystemStats(
    val hostname: String,
    val uptimeDays: Int,
    val uptimeHours: Int,
    val uptimeMinutes: Int,
    val uptimeSeconds: Int,
    val totalCores: Int,
    val physicalCores: Int,
    val totalInstalledMemory: Double,
    val systemName: String,
    val systemSerial: String,
    val timeZone: String,
    val systemManufacturer: String,
    val systemVersion: String,
    val cpuName: String

)
