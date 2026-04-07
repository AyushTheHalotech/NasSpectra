package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class PoolDataDto(
    val id: Int,
    val name: String,
    val status: String,
    val healthy: Boolean,
    val status_code: String,
    val size: Long,
    val allocated: Long,
    val free: Long,
    val scan: DiskScanDto,
    val topology: TopologyDto,
    val path: String
)
