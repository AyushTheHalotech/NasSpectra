package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.PoolDataDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState

fun PoolDataDto.toDomain(): PoolState {
    return PoolState(
        id = id,
        name = name,
        status = status,
        healthy = healthy,
        staus_code = status_code,
        size = bytesToGB(size),
        allocated = bytesToGB(allocated),
        free = bytesToGB(free),
        scan_function = scan.function,
        scan_state = scan.state,
        scan_errors = scan.errors,
        topology_disk = topology.data.firstOrNull()?.disk ?: "",
        topology_status = topology.data.firstOrNull()?.status ?: "",
        topology_read_errors = topology.data.firstOrNull()?.stats?.read_errors ?: 0,
        topology_write_errors = topology.data.firstOrNull()?.stats?.write_errors ?: 0,
        topology_checksum_errors = topology.data.firstOrNull()?.stats?.checksum_errors ?: 0,
        health_calculated = calculateHealth(healthy, status, status_code),
        path = path,
        device = topology.data.firstOrNull()?.device ?: "",
        disk = topology.data.firstOrNull()?.disk ?: ""

    )
}

private fun bytesToGB(bytes: Long): Double {
    return bytes / (1024 * 1024 * 1024.0)
}

private fun calculateHealth(healthy: Boolean, status: String, statusCode: String): String {
    if(healthy && status == "ONLINE" && statusCode == "OK") {
        return "Healthy"
    }
    return "Unhealthy"
}