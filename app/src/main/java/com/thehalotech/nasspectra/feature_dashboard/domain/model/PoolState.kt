package com.thehalotech.nasspectra.feature_dashboard.domain.model

data class PoolState(
    val id: Int,
    val name: String,
    val status: String,
    val healthy: Boolean,
    val staus_code: String,
    val size: Double,
    val allocated: Double,
    val free: Double,
    val scan_function: String,
    val scan_state: String,
    val scan_errors: Int,
    val topology_disk: String,
    val topology_status: String,
    val topology_read_errors: Int,
    val topology_write_errors: Int,
    val topology_checksum_errors: Int,
    val health_calculated: String,
    val path: String,
    val device: String,
    val disk: String
)
